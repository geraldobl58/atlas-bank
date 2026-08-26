package com.atlas_bank.atlas_bank.transaction.fraud;

import java.math.BigDecimal;
import java.util.UUID;

public interface FraudChecker {
    FraudCheckResult check(UUID accountId, BigDecimal amount);
}
