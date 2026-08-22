package com.atlas_bank.atlas_bank.controller;

import com.atlas_bank.atlas_bank.model.Account;
import com.atlas_bank.atlas_bank.model.Transaction;
import com.atlas_bank.atlas_bank.repository.TransactionRepository;
import com.atlas_bank.atlas_bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findById(id));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam UUID fromId, @RequestParam UUID toId, @RequestParam BigDecimal amount) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.transfer(fromId, toId, amount));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionRepository.findBySourceAccountIdOrTargetAccountId(id, id));
    }
}
