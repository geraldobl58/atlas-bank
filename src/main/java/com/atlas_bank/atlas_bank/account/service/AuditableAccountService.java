package com.atlas_bank.atlas_bank.account.service;

import com.atlas_bank.atlas_bank.account.model.Account;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements IAccountService {

    private final IAccountService delegate;

    public AuditableAccountService(@Qualifier("accountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    @PostConstruct
    public void init() {
        log.info("Class initialized: {}", delegate.getClass().getName());
    }

    @Override
    public Account create(Account account) {
        log.info("Creating account - number: {}, owner: {}",
                account.getAccountNumber(), account.getOwnerName());

        Account created = delegate.create(account);
        log.info("Account created - id: {}, number: {}, owner: {}",
                created.getId(), created.getAccountNumber(), created.getOwnerName());
        return created;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(UUID id) {
        return delegate.findById(id);
    }
}
