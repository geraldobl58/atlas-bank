package com.atlas_bank.atlas_bank.repository;

import com.atlas_bank.atlas_bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findBySourceAccountIdOrTargetAccountId(UUID sourceId, UUID targetId);
}