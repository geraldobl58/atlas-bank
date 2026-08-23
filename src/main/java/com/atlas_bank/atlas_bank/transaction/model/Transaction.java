package com.atlas_bank.atlas_bank.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "EXECUTED";
    }
}