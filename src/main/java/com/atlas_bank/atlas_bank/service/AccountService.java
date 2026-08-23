package com.atlas_bank.atlas_bank.service;

import com.atlas_bank.atlas_bank.model.Account;
import com.atlas_bank.atlas_bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found")
        );
    }
}
