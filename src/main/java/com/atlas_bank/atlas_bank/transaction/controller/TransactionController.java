package com.atlas_bank.atlas_bank.transaction.controller;

import com.atlas_bank.atlas_bank.transaction.dto.TransactionRequest;
import com.atlas_bank.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas_bank.atlas_bank.transaction.service.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
class TransactionController {
    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransactionRequest request) {
        Transaction transaction = transferService.execute(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount()
        );

        return ResponseEntity.status(HttpStatus.OK).body(toResponse(transaction));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable UUID id) {
        List<TransactionResponse> responses = transactionQueryService.getByAccountId(id)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setSourceAccountId(transaction.getSourceAccountId());
        response.setTargetAccountId(transaction.getTargetAccountId());
        response.setAmount(transaction.getAmount());
        response.setFee(transaction.getFee());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
}
