package com.atlas_bank.atlas_bank.transaction.service.listener;

import com.atlas_bank.atlas_bank.transaction.service.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener
    public void onTransactionExecutedEvent(TransactionExecutedEvent event) {
        log.info("Send notification of {} by ${} - Transaction #{}",
                event.type(), event.amount(), event.transactionId()
                );
    }
}
