package com.atlas_bank.atlas_bank.transaction.service.factory;

import com.atlas_bank.atlas_bank.transaction.enums.TransactionStatus;
import com.atlas_bank.atlas_bank.transaction.enums.TransactionType;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.model.state.PendingState;
import com.atlas_bank.atlas_bank.transaction.service.transfer.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTransfer(TransferContext ctx, BigDecimal fee) {
        Transaction transaction = Transaction.builder()
                        .type(TransactionType.TRANSFER)
                        .sourceAccountId(ctx.sourceAccountId().getId())
                        .targetAccountId(ctx.targetAccountId().getId())
                        .amount(ctx.amount())
                        .fee(fee)
                        .status(TransactionStatus.PENDING)
                                .build();

        transaction.advancedTo(new PendingState());

        return transaction;
    }
}