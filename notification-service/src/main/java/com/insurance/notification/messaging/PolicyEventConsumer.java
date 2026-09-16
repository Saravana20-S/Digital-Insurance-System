package com.insurance.notification.messaging;

import com.insurance.notification.config.RabbitMQConfig;
import com.insurance.notification.dto.PolicyActivatedEvent;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PolicyEventConsumer {


    /**
     * Receives policy activation events from RabbitMQ.
     */
    @RabbitListener(
            queues = RabbitMQConfig.QUEUE
    )
    public void consumePolicyActivated(
            PolicyActivatedEvent event) {

        log.info(
                "================================================"
        );

        log.info(
                "POLICY ACTIVATION EVENT RECEIVED"
        );

        log.info(
                "Policy ID   : {}",
                event.getPolicyId()
        );

        log.info(
                "Customer ID : {}",
                event.getCustomerId()
        );

        log.info(
                "Plan ID     : {}",
                event.getPlanId()
        );

        log.info(
                "Plan Name   : {}",
                event.getPlanName()
        );

        log.info(
                "Message     : {}",
                event.getMessage()
        );

        log.info(
                "Notification processing completed"
        );

        log.info(
                "================================================"
        );
    }
}