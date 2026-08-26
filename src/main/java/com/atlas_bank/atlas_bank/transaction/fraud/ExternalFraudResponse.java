package com.atlas_bank.atlas_bank.transaction.fraud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalFraudResponse {
    private String riskLevel;
    private double score;
    private String recommendation;
}
