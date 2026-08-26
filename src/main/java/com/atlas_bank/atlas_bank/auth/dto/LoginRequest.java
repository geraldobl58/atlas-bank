package com.atlas_bank.atlas_bank.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username ou email cadastrado no Keycloak", example = "geraldobl58")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Senha do usuário", example = "123456")
    private String password;
}
