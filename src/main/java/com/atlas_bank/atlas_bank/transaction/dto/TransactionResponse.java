package com.atlas_bank.atlas_bank.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {
    private UUID id;
    private String type;
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String status;
    private LocalDateTime createdAt;
}
