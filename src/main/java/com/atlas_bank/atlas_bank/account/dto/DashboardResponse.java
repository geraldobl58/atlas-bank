package com.atlas_bank.atlas_bank.account.dto;

import com.atlas_bank.atlas_bank.transaction.dto.TransactionResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DashboardResponse {
    private UUID accountId;
    private String accountName;
    private String accountNumber;
    private String ownerName;
    private String type;
    private BigDecimal balance;
    private String status;
    private List<TransactionResponse> recentTransactions;
}
