package com.atlas_bank.atlas_bank.transaction.validation.chain;

import com.atlas_bank.atlas_bank.transaction.service.transfer.TransferContext;

public interface TransferValidator {
    void validate(TransferContext ctx);
}
