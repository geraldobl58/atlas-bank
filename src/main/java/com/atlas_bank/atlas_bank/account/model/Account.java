package com.atlas_bank.atlas_bank.account.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type;
    private BigDecimal balance;
    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (status == null) status = "ACTIVE";
        if (balance == null) balance = BigDecimal.ZERO;
    }
}
