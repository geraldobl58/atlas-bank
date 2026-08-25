package com.atlas_bank.atlas_bank.transaction.service.transfer;

import com.atlas_bank.atlas_bank.transaction.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITransferService {
    Transaction execute(UUID fromId, UUID toId, BigDecimal amount);
}
