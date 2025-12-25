package com.lxp.auth.application.event;

import com.lxp.auth.application.event.mapper.IntegrationEventMapper;
import com.lxp.auth.domain.common.event.AuthCreatedEvent;
import com.lxp.common.application.port.in.DomainEventHandler;
import com.lxp.common.application.port.out.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalAuthEventHandler implements DomainEventHandler<AuthCreatedEvent> {

    private final IntegrationEventPublisher integrationEventPublisher;
    private final IntegrationEventMapper integrationEventMapper;

    @Override
    public void handle(AuthCreatedEvent event) {
        log.info("Handling AuthCreatedEvent: userId={}", event.getAggregateId());
        try {
            integrationEventPublisher.publish(integrationEventMapper.toIntegration(event));
            log.info("Successfully published Integration Event for userId={}", event.getAggregateId());
        } catch (Exception e) {
            log.error("Fail to publish Integration Event for userId={}", event.getAggregateId(), e);
        }
    }

    @Override
    public Class<AuthCreatedEvent> supportedEventType() {
        return AuthCreatedEvent.class;
    }
}
