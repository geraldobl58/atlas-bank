package com.atlas_bank.atlas_bank.shared.exception;

import com.atlas_bank.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas_bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas_bank.atlas_bank.transaction.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(AccountNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problem.setTitle("Account Not Found");
        return problem;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFunds(InsufficientFundsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422),
                ex.getMessage()
        );
        problem.setTitle("Insufficient Funds");
        return problem;
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ProblemDetail handleAccountNotActive(AccountNotActiveException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422),
                ex.getMessage()
        );
        problem.setTitle("Account Not Active");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Invalid Request");

        List<String> errors = new ArrayList<>();
                ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

                ex.getBindingResult()
                                .getGlobalErrors()
                                        .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleAllExceptions(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
        problem.setTitle("Internal Server Error");
        return problem;
    }
}
