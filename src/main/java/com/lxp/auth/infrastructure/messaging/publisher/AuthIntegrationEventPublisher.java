package com.lxp.auth.infrastructure.messaging.publisher;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthIntegrationEventPublisher implements IntegrationEventPublisher {

    private static final String EXCHANGE_NAME = "auth.events";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(IntegrationEvent integrationEvent) {
        this.publish(EXCHANGE_NAME, integrationEvent);
    }

    @Override
    public void publish(String topic, IntegrationEvent integrationEvent) {
        rabbitTemplate.convertAndSend(topic, integrationEvent.getEventType(), integrationEvent);
    }
}
