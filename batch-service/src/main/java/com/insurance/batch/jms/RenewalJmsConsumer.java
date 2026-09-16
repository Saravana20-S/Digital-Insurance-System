package com.insurance.batch.jms;

import com.insurance.batch.dto.RenewalEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RenewalJmsConsumer {

    @JmsListener(destination = JmsConfig.RENEWAL_QUEUE)
    public void consumeRenewalEvent(RenewalEvent event) {

        log.info("Renewal event received: policyId={}, customerId={}",
                event.getPolicyId(),
                event.getCustomerId());

        log.info("Premium amount: {}", event.getPremiumAmount());
        log.info("Due date: {}", event.getDueDate());

        log.info("Renewal processing completed for policyId={}",
                event.getPolicyId());
    }
}