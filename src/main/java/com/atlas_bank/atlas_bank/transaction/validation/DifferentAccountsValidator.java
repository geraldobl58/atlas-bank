package com.atlas_bank.atlas_bank.transaction.validation;

import com.atlas_bank.atlas_bank.transaction.dto.TransactionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransactionRequest> {
    @Override
    public boolean isValid(TransactionRequest transactionRequest, ConstraintValidatorContext context) {
        if (transactionRequest.getSourceAccountId() == null || transactionRequest.getTargetAccountId() == null) {
           return true;
        }

        return !transactionRequest.getSourceAccountId().equals(transactionRequest.getTargetAccountId());
    }
}
