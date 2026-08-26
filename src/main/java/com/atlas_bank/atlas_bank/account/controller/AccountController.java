package com.atlas_bank.atlas_bank.account.controller;

import com.atlas_bank.atlas_bank.account.dto.AccountMapper;
import com.atlas_bank.atlas_bank.account.dto.AccountResponse;
import com.atlas_bank.atlas_bank.account.dto.CreateAccountRequest;
import com.atlas_bank.atlas_bank.account.model.Account;
import com.atlas_bank.atlas_bank.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Gerenciamento de contas bancárias")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final IAccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    @Operation(summary = "Criar conta", description = "Cria uma nova conta bancária. Requer role ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou campos obrigatórios ausentes"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer role ADMIN")
    })
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account saved = accountService.create(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Retorna todas as contas cadastradas. Requer role ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer role ADMIN")
    })
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Retorna uma conta pelo seu identificador único. Requer role USER ou ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta encontrada",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer role USER ou ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<AccountResponse> findById(
            @Parameter(description = "ID da conta", required = true, example = "3239a6dd-66f5-46cc-965f-9417f0737dbf")
            @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountMapper.toResponse(accountService.findById(id)));
    }
}
