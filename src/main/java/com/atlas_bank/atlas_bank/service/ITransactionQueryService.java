package com.atlas_bank.atlas_bank.service;

import com.atlas_bank.atlas_bank.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface ITransactionQueryService {
    List<Transaction> getByAccountId(UUID accountId);
}
