package com.atlas_bank.atlas_bank.transaction.service.transfer;

import com.atlas_bank.atlas_bank.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account sourceAccountId, Account targetAccountId, BigDecimal amount) {
}
