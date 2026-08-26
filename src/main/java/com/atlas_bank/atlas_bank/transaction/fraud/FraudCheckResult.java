package com.atlas_bank.atlas_bank.transaction.fraud;

public record FraudCheckResult(boolean blocked, String reason) {
    public static FraudCheckResult allowed() {
        return new FraudCheckResult(false, null);
    }

    public static FraudCheckResult blocked(String reason) {
        return new FraudCheckResult(true, reason);
    }
}
