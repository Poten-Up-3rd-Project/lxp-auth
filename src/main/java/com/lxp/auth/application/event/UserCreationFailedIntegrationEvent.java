package com.lxp.auth.application.event;

import com.lxp.common.application.event.BaseIntegrationEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreationFailedIntegrationEvent extends BaseIntegrationEvent {
    private final String userId;
    private final String reason;

    protected UserCreationFailedIntegrationEvent(String eventId, LocalDateTime occurredAt, String source, String correlationId, String causationId, int version, String userId, String reason) {
        super(eventId, occurredAt, source, correlationId, causationId, version);
        this.userId = userId;
        this.reason = reason;
    }
}
