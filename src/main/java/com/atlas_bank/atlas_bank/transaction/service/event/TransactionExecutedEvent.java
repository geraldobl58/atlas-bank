package com.atlas_bank.atlas_bank.transaction.service.event;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionExecutedEvent(
        UUID transactionId,
        String type,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal amount,
        BigDecimal fee
) {
}
