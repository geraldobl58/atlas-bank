package com.atlas_bank.atlas_bank.transaction.controller;

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
    public ResponseEntity<Transaction> transfer(@RequestParam UUID fromId, @RequestParam UUID toId, @RequestParam BigDecimal amount) {
        return ResponseEntity.status(HttpStatus.OK).body(transferService.execute(fromId, toId, amount));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionQueryService.getByAccountId(id));
    }
}
