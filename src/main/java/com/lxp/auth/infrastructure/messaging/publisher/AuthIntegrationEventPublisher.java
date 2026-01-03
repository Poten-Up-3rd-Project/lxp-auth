package com.lxp.auth.infrastructure.messaging.publisher;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
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
        try {
            rabbitTemplate.convertAndSend(topic, integrationEvent.getEventType(), integrationEvent);
            log.info(
                "Successfully published integration event: eventId={}, eventType={}, exchange={}",
                integrationEvent.getEventId(), integrationEvent.getEventType(), topic
            );
        } catch (AmqpException e) {
            log.error(
                "Failed to publish integration event: eventId={}, eventType={}, exchange={}",
                integrationEvent.getEventId(), integrationEvent.getEventType(), topic
            );
            throw e;
        }
    }
}
