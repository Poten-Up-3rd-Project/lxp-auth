package com.lxp.auth.domain.common.event;

import com.lxp.common.domain.event.BaseDomainEvent;

import java.time.LocalDateTime;
import java.util.List;

public class AuthCreatedEvent extends BaseDomainEvent implements CrudEvent {
    private final String email;
    private final String name;
    private final String role;
    private final List<Long> tagIds;
    private final String level;

    public AuthCreatedEvent(String aggregateId, String email, String name, String role, List<Long> tagIds, String level) {
        super(aggregateId);
        this.email = email;
        this.name = name;
        this.role = role;
        this.tagIds = tagIds;
        this.level = level;
    }

    public AuthCreatedEvent(String eventId, String aggregateId, LocalDateTime occurredAt, String email, String name, String role, List<Long> tagIds, String level) {
        super(eventId, aggregateId, occurredAt);
        this.email = email;
        this.name = name;
        this.role = role;
        this.tagIds = tagIds;
        this.level = level;
    }

    @Override
    public CrudType getCrudType() {
        return CrudType.CREATED;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public String getLevel() {
        return level;
    }
}
