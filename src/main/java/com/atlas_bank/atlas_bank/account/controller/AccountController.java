package com.atlas_bank.atlas_bank.account.controller;

import com.atlas_bank.atlas_bank.account.model.Account;
import com.atlas_bank.atlas_bank.account.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

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


}
