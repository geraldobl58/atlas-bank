package com.atlas_bank.atlas_bank.transaction.controller;

import com.atlas_bank.atlas_bank.transaction.dto.TransactionMapper;
import com.atlas_bank.atlas_bank.transaction.dto.TransactionRequest;
import com.atlas_bank.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas_bank.atlas_bank.transaction.service.ITransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
class TransactionController {
    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transferService.execute(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount()
        );

        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.toResponse(transaction));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable UUID id) {
        List<TransactionResponse> responses = transactionQueryService.getByAccountId(id)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
