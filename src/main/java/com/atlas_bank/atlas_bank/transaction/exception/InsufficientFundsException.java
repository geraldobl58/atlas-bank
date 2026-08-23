package com.atlas_bank.atlas_bank.transaction.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID accountId, BigDecimal balance, BigDecimal amount) {
        super(String.format("Insufficient funds in account %s. Current balance: %.2f, attempted withdrawal: %.2f", accountId, balance, amount));
    }
}
