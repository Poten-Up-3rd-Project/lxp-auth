package com.lxp.auth.application.event.mapper;

import com.lxp.auth.application.event.UserRegisteredIntegrationEvent;
import com.lxp.auth.domain.common.event.AuthCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class IntegrationEventMapper {

    public UserRegisteredIntegrationEvent toIntegration(AuthCreatedEvent domainEvent) {
        return new UserRegisteredIntegrationEvent(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            "auth-service",
            null,
            null,
            1,
            "auth.user." + domainEvent.getCrudType().name(),
            new UserRegisteredIntegrationEvent.Payload(
                domainEvent.getAggregateId(),
                domainEvent.name(),
                domainEvent.email(),
                domainEvent.role(),
                domainEvent.tagIds(),
                domainEvent.level()
            )
        );
    }

}
