package com.atlas_bank.atlas_bank.account.service;

import com.atlas_bank.atlas_bank.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Primary
public class CachedAccountService implements IAccountService{
    private final IAccountService delegate;
    private final Map<UUID, Account> cache = new ConcurrentHashMap<>();

    public CachedAccountService(@Qualifier("auditableAccountService") IAccountService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Account create(Account account) {
        Account created = delegate.create(account);
        cache.put(created.getId(), created);

        log.info("Account {} aggregate in ", created.getId());

        return created;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(UUID id) {
        Account cached = cache.get(id);

        if (cached != null) {
            log.info("Account {} found in cache ", id);
            return cached;
        }

        log.info("Account {} not exists cache ", id);

        Account account = delegate.findById(id);

        cache.put(id, account);

        return account;
    }
}
