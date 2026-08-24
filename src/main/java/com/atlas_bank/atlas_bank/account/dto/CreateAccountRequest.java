package com.atlas_bank.atlas_bank.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Type of account is required")
    private String type;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private BigDecimal balance;
}
