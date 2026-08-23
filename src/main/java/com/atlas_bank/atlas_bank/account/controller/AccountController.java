package com.atlas_bank.atlas_bank.account.controller;

import com.atlas_bank.atlas_bank.account.dto.AccountMapper;
import com.atlas_bank.atlas_bank.account.dto.AccountResponse;
import com.atlas_bank.atlas_bank.account.dto.CreateAccountRequest;
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
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);

        Account saved = accountService.create(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountMapper.toResponse(accountService.findById(id)));
    }
}
