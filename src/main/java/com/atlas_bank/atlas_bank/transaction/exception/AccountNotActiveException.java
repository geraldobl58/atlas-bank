package com.atlas_bank.atlas_bank.transaction.exception;

import java.util.UUID;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(UUID accountId, String status) {
        super(String.format("Account %s is not active. Current status: %s", accountId, status));
    }
}
