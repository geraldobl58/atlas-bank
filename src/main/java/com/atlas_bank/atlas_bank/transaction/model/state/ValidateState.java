package com.atlas_bank.atlas_bank.transaction.model.state;

import com.atlas_bank.atlas_bank.transaction.enums.TransactionStatus;

public record ValidateState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.VALIDATED;
    }

    @Override
    public TransactionState reject(String reason) {
        return new RejectedState();
    }

    @Override
    public TransactionState execute() {
        return new ExecutedState();
    }
}
