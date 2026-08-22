package com.atlas_bank.atlas_bank.model;

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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER

    private UUID sourceAccountId;

    private UUID targetAccountId;

    private BigDecimal amount;

    private BigDecimal fee;

    private String status; // PENDING, EXECUTED, REJECTED

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "EXECUTED";
    }
}