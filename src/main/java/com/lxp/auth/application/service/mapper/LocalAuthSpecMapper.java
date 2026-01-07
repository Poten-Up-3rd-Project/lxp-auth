package com.lxp.auth.application.service.mapper;

import com.lxp.auth.application.port.provided.command.LocalAuthRegisterCommand;
import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.domain.local.service.spec.AuthCreateSpec;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthSpecMapper {

    public AuthCreateSpec toAuthCreateSpec(LocalAuthRegisterCommand command) {
        return new AuthCreateSpec(
            command.email(), command.password()
        );
    }

    public CreateUserCommand toCreateUserCommand(LocalAuthRegisterCommand command, UserId userId) {
        return new CreateUserCommand(
            userId.asString(),
            command.name(),
            command.email(),
            command.role(),
            command.tagIds(),
            command.level()
        );
    }

}
