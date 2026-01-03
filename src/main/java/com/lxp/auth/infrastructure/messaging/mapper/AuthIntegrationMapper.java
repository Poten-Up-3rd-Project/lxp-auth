package com.lxp.auth.infrastructure.messaging.mapper;

import com.lxp.auth.application.event.UserCreationFailedIntegrationEvent;
import com.lxp.auth.application.port.in.command.LocalAuthRemoveCommand;
import com.lxp.auth.domain.common.model.vo.UserId;
import org.springframework.stereotype.Component;

@Component
public class AuthIntegrationMapper {

    public LocalAuthRemoveCommand toRemoveCommand(UserCreationFailedIntegrationEvent event) {
        return new LocalAuthRemoveCommand(UserId.of(event.getUserId()));
    }

}
