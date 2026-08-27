package com.atlas_bank.atlas_bank.transaction.model.state;

import com.atlas_bank.atlas_bank.transaction.enums.TransactionStatus;

public sealed interface TransactionState permits PendingState, ValidateState, ExecutedState, RejectedState, ReversedState {
    TransactionStatus status();

    default TransactionState validate() {
        throw new IllegalStateException("Don't can validate state transaction " + status());
    }

    default TransactionState execute() {
        throw new IllegalStateException("Don't can executed state transaction " + status());
    }

    default TransactionState reject(String reason) {
        throw new IllegalStateException("Don't can rejected state transaction " + status());
    }

    default TransactionState reverse() {
        throw new IllegalStateException("Don't can reverse state transaction " + status());
    }
}
