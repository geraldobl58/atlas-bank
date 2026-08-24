package com.atlas_bank.atlas_bank.transaction.service;

import com.atlas_bank.atlas_bank.account.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account sourceAccountId, Account targetAccountId, BigDecimal amount) {
}
