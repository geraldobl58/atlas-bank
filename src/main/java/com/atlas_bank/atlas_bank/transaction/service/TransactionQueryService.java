package com.atlas_bank.atlas_bank.transaction.service;

import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionQueryService implements ITransactionQueryService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getByAccountId(UUID accountId) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
