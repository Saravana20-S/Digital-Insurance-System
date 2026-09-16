package com.insurance.policy.jms;

import com.insurance.policy.messaging.RenewalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RenewalJmsProducer {

    private final JmsTemplate jmsTemplate;

    public void sendRenewal(RenewalEvent event) {

        log.info(
                "Sending renewal JMS event: policyId={}, customerId={}, amount={}",
                event.getPolicyId(),
                event.getCustomerId(),
                event.getPremiumAmount()
        );

        jmsTemplate.convertAndSend(
                JmsConfig.RENEWAL_QUEUE,
                event
        );

        log.info(
                "Renewal JMS event sent successfully: policyId={}",
                event.getPolicyId()
        );
    }
}