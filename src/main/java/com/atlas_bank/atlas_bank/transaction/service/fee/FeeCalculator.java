package com.atlas_bank.atlas_bank.transaction.service.fee;

import com.atlas_bank.atlas_bank.account.enums.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    boolean supports(AccountType accountType);
    BigDecimal calculateFee(BigDecimal amount);
}
