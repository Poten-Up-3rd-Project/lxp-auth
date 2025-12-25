package com.lxp.auth.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    @Bean
    public TopicExchange authEventsExchange() {
        return new TopicExchange("auth.events", true, false);
    }

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange("user.events", true, false);
    }

    // Queues
    @Bean
    public Queue userCreationFailedQueue() {
        return new Queue("user-events.user-creation-failed", true);
    }

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue("auth-events.user-registered", true);
    }

    // Bindings
    @Bean
    public Binding userCreationFailedBinding(Queue userCreationFailedQueue, TopicExchange userEventsExchange) {
        return BindingBuilder.bind(userCreationFailedQueue)
                .to(userEventsExchange)
                .with("UserCreationFailedIntegrationEvent");
    }

    @Bean
    public Binding userRegisteredBinding(Queue userRegisteredQueue, TopicExchange authEventsExchange) {
        return BindingBuilder.bind(userRegisteredQueue)
                .to(authEventsExchange)
                .with("UserRegisteredIntegrationEvent");
    }
}
