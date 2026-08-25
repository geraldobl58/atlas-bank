package com.atlas_bank.atlas_bank.transaction.service.fee;

import com.atlas_bank.atlas_bank.account.enums.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class SavingsFeeCalculator implements FeeCalculator {
    @Override
    public boolean supports(AccountType accountType) {
        return accountType == AccountType.SAVING;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01")); // 1% fee for savings accounts
    }
}
