package com.atlas_bank.atlas_bank.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CheckingFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(String accountType) {
        return "CHECKING".equals(accountType);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.015"));
    }
}
