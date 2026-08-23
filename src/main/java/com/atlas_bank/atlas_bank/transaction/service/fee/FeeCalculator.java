package com.atlas_bank.atlas_bank.transaction.service.fee;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(String accountType);
    BigDecimal calculateFee(BigDecimal amount);
}
