package com.atlas_bank.atlas_bank.account.model;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
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
