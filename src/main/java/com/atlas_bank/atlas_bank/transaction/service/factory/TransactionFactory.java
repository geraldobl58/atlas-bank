package com.atlas_bank.atlas_bank.transaction.service.factory;

import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.service.transfer.TransferContext;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction createTransfer(TransferContext ctx, BigDecimal fee) {
        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setCreatedBy("SYSTEM");
        transaction.setSourceAccountId(ctx.sourceAccountId().getId());
        transaction.setTargetAccountId(ctx.targetAccountId().getId());
        transaction.setAmount(ctx.amount());
        transaction.setFee(fee);
        transaction.setStatus("COMPLETED");
        return transaction;
    }
}

