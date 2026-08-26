package com.atlas_bank.atlas_bank.transaction.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
public class ExternalFraudCheckAdapter implements FraudChecker {

    @Override
    public FraudCheckResult check(UUID accountId, BigDecimal amount) {
        ExternalFraudResponse response = callExternalApi(accountId, amount);

        log.info("External fraud check response for account: {} - Risk Level: {}, Score: {}, Recommendation: {}",
                accountId, response.getRiskLevel(), response.getScore(), response.getRecommendation());

        if ("BLOCK".equals(response.getRecommendation())) {
           return FraudCheckResult.blocked(
                   "Operation blocked for risk " + response.getRiskLevel() + "(score: " + response.getScore() + ")"
           );
        }

        return FraudCheckResult.allowed();
    }

    private ExternalFraudResponse callExternalApi(UUID accountId, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("1000000")) > 0) {
            return new ExternalFraudResponse("HIGH", 0.95, "BLOCK");
        }
        return new ExternalFraudResponse("LOW", 0.1, "ALLOW");
    }

}
