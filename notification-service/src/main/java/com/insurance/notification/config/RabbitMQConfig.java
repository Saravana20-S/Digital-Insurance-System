package com.insurance.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE =
            "insurance.exchange";

    public static final String QUEUE =
            "policy.activation.queue";

    public static final String ROUTING_KEY =
            "policy.activated";


    @Bean
    public TopicExchange insuranceExchange() {

        return new TopicExchange(
                EXCHANGE,
                true,
                false
        );
    }


    @Bean
    public Queue policyActivationQueue() {

        return QueueBuilder
                .durable(QUEUE)
                .build();
    }


    @Bean
    public Binding policyActivationBinding(
            Queue policyActivationQueue,
            TopicExchange insuranceExchange) {

        return BindingBuilder
                .bind(policyActivationQueue)
                .to(insuranceExchange)
                .with(ROUTING_KEY);
    }
}