package com.lxp.auth.application.event;

import com.lxp.auth.application.event.mapper.IntegrationEventMapper;
import com.lxp.auth.domain.common.event.AuthCreatedEvent;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.common.application.port.in.DomainEventHandler;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalAuthEventHandler implements DomainEventHandler<AuthCreatedEvent> {

    private final IntegrationEventPublisher integrationEventPublisher;
    private final IntegrationEventMapper integrationEventMapper;

    @Override
    public void handle(AuthCreatedEvent event) throws AuthException {
        log.info("Handling AuthCreatedEvent: userId={}", event.getAggregateId());
        try {
            integrationEventPublisher.publish(integrationEventMapper.toIntegration(event));
            log.info("Successfully published Integration Event for userId={}, eventId={}, eventType{}",
                event.getAggregateId(), event.getEventId(), event.getEventType()
            );
        } catch (AmqpException e) {
            log.error("Failed to publish Integration Event to RabbitMQ for userId={}", event.getAggregateId(), e);
            throw new AuthException(AuthErrorCode.EVENT_PUBLISH_FAILED, "Failed to publish user registration event", e);
        } catch (Exception e) {
            log.error("Unexpected error while publishing Integration Event for userId={}", event.getAggregateId(), e);
            throw new AuthException(AuthErrorCode.INTERNAL_ERROR, "Unexpected error during event publishing", e);
        }
    }

    @Override
    public Class<AuthCreatedEvent> supportedEventType() {
        return AuthCreatedEvent.class;
    }
}
