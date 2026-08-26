package com.atlas_bank.atlas_bank.transaction.controller;

import com.atlas_bank.atlas_bank.transaction.dto.TransactionMapper;
import com.atlas_bank.atlas_bank.transaction.dto.TransactionRequest;
import com.atlas_bank.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas_bank.atlas_bank.transaction.model.Transaction;
import com.atlas_bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas_bank.atlas_bank.transaction.service.transfer.ITransferService;
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
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Operações de transferência e consulta de transações")
@SecurityRequirement(name = "bearerAuth")
class TransactionController {

    private final ITransferService transferService;
    private final ITransactionQueryService transactionQueryService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    @Operation(summary = "Realizar transferência", description = "Executa uma transferência entre duas contas. Requer role USER ou ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos — campos obrigatórios ausentes ou contas iguais"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer role USER ou ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada"),
            @ApiResponse(responseCode = "422", description = "Saldo insuficiente ou conta inativa")
    })
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transferService.execute(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount()
        );
        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.toResponse(transaction));
    }

    @GetMapping("/{id}/transactions")
    @Operation(summary = "Listar transações da conta", description = "Retorna todas as transações associadas a uma conta. Requer role USER ou ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações retornadas com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer role USER ou ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @Parameter(description = "ID da conta", required = true, example = "3239a6dd-66f5-46cc-965f-9417f0737dbf")
            @PathVariable UUID id) {
        List<TransactionResponse> responses = transactionQueryService.getByAccountId(id)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
