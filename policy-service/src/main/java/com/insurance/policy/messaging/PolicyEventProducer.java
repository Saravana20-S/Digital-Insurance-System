package com.insurance.policy.messaging;

import com.insurance.policy.config.RabbitMQConfig;
import com.insurance.policy.dto.event.PolicyActivatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PolicyEventProducer {

    private final RabbitTemplate rabbitTemplate;


    /**
     * Publish policy activation event.
     */
    public void publishPolicyActivated(
            PolicyActivatedEvent event) {

        log.info(
                "Publishing policy activation event: policyId={}",
                event.getPolicyId()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );

        log.info(
                "Policy activation event published: policyId={}",
                event.getPolicyId()
        );
    }
}