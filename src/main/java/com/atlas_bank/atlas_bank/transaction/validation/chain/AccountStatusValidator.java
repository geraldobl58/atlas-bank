package com.atlas_bank.atlas_bank.transaction.validation.chain;

import com.atlas_bank.atlas_bank.account.enums.AccountStatus;
import com.atlas_bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.atlas_bank.transaction.service.transfer.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AccountStatusValidator implements  TransferValidator{
    @Override
    public void validate(TransferContext ctx) {
        if (ctx.sourceAccountId().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(ctx.sourceAccountId().getId(), ctx.sourceAccountId().getStatus().name());
        }

        if (ctx.targetAccountId().getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(ctx.targetAccountId().getId(), ctx.targetAccountId().getStatus().name());
        }
    }
}
