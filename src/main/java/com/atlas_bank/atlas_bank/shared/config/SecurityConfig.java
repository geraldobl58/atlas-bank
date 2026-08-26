package com.atlas_bank.atlas_bank.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> auth
                                // ACCOUNTS
                                .requestMatchers(HttpMethod.POST, "/api/v1/accounts").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/accounts").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/accounts/{id}").hasAnyRole("USER", "ADMIN")

                                // TRANSACTIONS
                                .requestMatchers(HttpMethod.POST, "/api/v1/transactions/transfer").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/transactions/{id}/transactions").hasAnyRole("USER", "ADMIN")

                                // AUTH
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                                // POSTGRES
                                .requestMatchers("/postgres-console/**").permitAll()

                                // SWAGGER
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**",
                                        "/v3/api-docs.yaml"
                                ).permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var realmAccess = jwt.getClaimAsMap("realm_access");

            if (realmAccess == null || realmAccess.get("roles") == null) {
                return List.of();
            }

            var roles = (List<String>) realmAccess.get("roles");

            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return converter;
    }
}
