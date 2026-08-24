package com.atlas_bank.atlas_bank.transaction.service.fee;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class PremiumFeeCalculator implements FeeCalculator {

    @Override
    public boolean supports(String accountType) {
        return "PREMIUM".equals(accountType);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
