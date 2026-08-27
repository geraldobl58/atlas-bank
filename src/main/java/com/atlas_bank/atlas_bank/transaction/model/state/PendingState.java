package com.atlas_bank.atlas_bank.transaction.model.state;

import com.atlas_bank.atlas_bank.transaction.enums.TransactionStatus;

public record PendingState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.PENDING;
    }

    @Override
    public TransactionState validate() {
        return new ValidateState();
    }

    @Override
    public TransactionState reject(String reason) {
        return new RejectedState();
    }
}
