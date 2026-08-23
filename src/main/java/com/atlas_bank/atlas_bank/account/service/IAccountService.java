package com.atlas_bank.atlas_bank.account.service;

import com.atlas_bank.atlas_bank.account.model.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    Account create(Account account);
    List<Account> findAll();
    Account findById(UUID id);
}
