package com.atlas_bank.atlas_bank.account.controller;

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

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        account.setOwnerName(request.getOwnerName());
        account.setEmail(request.getEmail());
        account.setType(request.getType());
        account.setBalance(request.getBalance());

        Account saved = accountService.create(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(toResponse(accountService.findById(id)));
    }

    private AccountResponse toResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setOwnerName(account.getOwnerName());
        response.setEmail(account.getEmail());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus());
        response.setCreatedAt(account.getCreatedAt());
        return response;
    }

}
