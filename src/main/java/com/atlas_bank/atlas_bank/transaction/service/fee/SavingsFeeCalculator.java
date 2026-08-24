package com.atlas_bank.atlas_bank.transaction.service.fee;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class SavingsFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return "SAVINGS".equals(accountType);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01")); // 1% fee for savings accounts
    }
}
