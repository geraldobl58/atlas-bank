package com.atlas_bank.atlas_bank.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private BigDecimal amount;
}
