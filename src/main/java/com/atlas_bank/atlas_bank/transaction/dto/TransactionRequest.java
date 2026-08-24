package com.atlas_bank.atlas_bank.transaction.dto;

import com.atlas_bank.atlas_bank.transaction.validation.DifferentAccounts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@DifferentAccounts
public class TransactionRequest {
    @NotNull(message = "Source account ID cannot be null")
    private UUID sourceAccountId;

    @NotNull(message = "Target account ID cannot be null")
    private UUID targetAccountId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
