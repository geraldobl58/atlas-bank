package com.atlas_bank.atlas_bank.transaction.model.state;

import com.atlas_bank.atlas_bank.transaction.enums.TransactionStatus;

public record ReversedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REVERSED;
    }
}
