package com.atlas_bank.atlas_bank.account.repository;

import com.atlas_bank.atlas_bank.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
