package com.insurance.policy.jms;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Map;

@Configuration
@EnableJms
public class JmsConfig {

    public static final String RENEWAL_QUEUE = "insurance.renewal.queue";

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {

        JacksonJsonMessageConverter converter =
                new JacksonJsonMessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        // logical name instead of FQCN
        converter.setTypeIdMappings(
                Map.of("renewalEvent",
                        com.insurance.policy.messaging.RenewalEvent.class)
        );

        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter) {

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        return factory;
    }
}