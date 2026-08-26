package com.atlas_bank.atlas_bank.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginResponse {

    @JsonProperty("access_token")
    @Schema(description = "JWT token para autenticação nos demais endpoints")
    private String accessToken;

    @JsonProperty("expires_in")
    @Schema(description = "Tempo de expiração em segundos", example = "300")
    private int expiresIn;

    @JsonProperty("refresh_token")
    @Schema(description = "Token para renovar o access_token")
    private String refreshToken;

    @JsonProperty("refresh_expires_in")
    @Schema(description = "Tempo de expiração do refresh token em segundos", example = "1800")
    private int refreshExpiresIn;

    @JsonProperty("token_type")
    @Schema(description = "Tipo do token", example = "Bearer")
    private String tokenType;
}
