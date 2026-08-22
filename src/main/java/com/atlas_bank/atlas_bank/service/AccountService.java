package com.atlas_bank.atlas_bank.service;

import com.atlas_bank.atlas_bank.model.Account;
import com.atlas_bank.atlas_bank.model.Transaction;
import com.atlas_bank.atlas_bank.repository.AccountRepository;
import com.atlas_bank.atlas_bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found")
        );
    }

    @Transactional
    public Transaction transfer(UUID fromId, UUID toId, BigDecimal amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!"ACTIVE".equals(from.getStatus())) {
            throw new RuntimeException("From account is not active");
        }

        if (!"ACTIVE".equals(to.getStatus())) {
            throw new RuntimeException("To account is not active");
        }

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal fee;
        if ("SAVING".equals(from.getType())) {
            fee = amount.multiply(new BigDecimal("0.01"));
        } else if ("CHECKING".equals(from.getType())) {
            fee = amount.multiply(new BigDecimal("0.15"));
        } else {
            fee = BigDecimal.ZERO;
        }

        from.setBalance(from.getBalance().subtract(amount).subtract(fee));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setSourceAccountId(fromId);
        transaction.setTargetAccountId(toId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setStatus("COMPLETED");

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(UUID accountId) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
