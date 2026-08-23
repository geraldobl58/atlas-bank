package com.atlas_bank.atlas_bank.transaction.service;

import com.atlas_bank.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.atlas_bank.account.model.Account;
import com.atlas_bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.atlas_bank.transaction.exception.InsufficientFundsException;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.account.repository.AccountRepository;
import com.atlas_bank.atlas_bank.transaction.repository.TransactionRepository;
import com.atlas_bank.atlas_bank.transaction.service.fee.FeeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final List<FeeCalculator> feeCalculators;

    @Override
    @Transactional
    public Transaction execute(UUID fromId, UUID toId, BigDecimal amount) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        if (!"ACTIVE".equals(from.getStatus())) {
            throw new AccountNotActiveException(fromId, from.getStatus());
        }

        if (!"ACTIVE".equals(to.getStatus())) {
            throw new AccountNotActiveException(toId, to.getStatus());
        }

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(fromId, from.getBalance(), amount);
        }

        BigDecimal fee = feeCalculators
                .stream()
                .filter(fc -> fc.supports(from.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No fee calculator available " + from.getType()))
                .calculateFee(amount);


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
}
