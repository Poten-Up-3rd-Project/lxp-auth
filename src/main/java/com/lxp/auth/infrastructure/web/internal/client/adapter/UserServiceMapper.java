package com.lxp.auth.infrastructure.web.internal.client.adapter;

import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.infrastructure.web.internal.client.dto.CreateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserServiceMapper {

    public CreateUserRequest createUserRequest(CreateUserCommand command) {
        return new CreateUserRequest(
            command.userId(),
            command.name(),
            command.email(),
            command.role(),
            command.tagIds(),
            command.level()
        );
    }

}
