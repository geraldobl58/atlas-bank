package com.atlas_bank.atlas_bank.transaction.service.listener;

import com.atlas_bank.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {

    @EventListener
    public void onTransactionExecutedEvent(TransactionExecutedEvent event) {
        log.info("Register audit of {} by {} - Transaction #{} for ${}",
                event.type(), event.sourceAccountId(), event.transactionId(), event.amount());

    }
}
