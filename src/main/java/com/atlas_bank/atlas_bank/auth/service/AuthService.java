package com.atlas_bank.atlas_bank.auth.service;

import com.atlas_bank.atlas_bank.auth.dto.LoginRequest;
import com.atlas_bank.atlas_bank.auth.dto.LoginResponse;
import com.atlas_bank.atlas_bank.auth.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public LoginResponse login(LoginRequest request) {
        String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", request.getUsername());
        body.add("password", request.getPassword());

        if (clientSecret != null && !clientSecret.isBlank()) {
            body.add("client_secret", clientSecret);
        }

        try {
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                    tokenUrl,
                    new HttpEntity<>(body, headers),
                    LoginResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
