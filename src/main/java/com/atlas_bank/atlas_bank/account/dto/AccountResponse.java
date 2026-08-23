package com.atlas_bank.atlas_bank.account.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountResponse {
    private UUID id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type;
    private BigDecimal balance;
    private String status;
    private LocalDateTime createdAt;
}
