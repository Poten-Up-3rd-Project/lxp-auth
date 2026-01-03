package com.lxp.auth.infrastructure.web.internal.mapper;

import com.lxp.auth.application.port.in.command.LocalAuthLoginCommand;
import com.lxp.auth.application.port.in.command.LocalAuthRegisterCommand;
import com.lxp.auth.infrastructure.web.internal.dto.LoginRequest;
import com.lxp.auth.infrastructure.web.internal.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthInternalMapper {

    public LocalAuthLoginCommand localAuthLoginCommand(LoginRequest loginRequest) {
        return new LocalAuthLoginCommand(
            loginRequest.email(),
            loginRequest.password()
        );
    }

    public LocalAuthRegisterCommand localAuthRegisterCommand(RegisterRequest registerRequest) {
        return new LocalAuthRegisterCommand(
            registerRequest.email(),
            registerRequest.password(),
            registerRequest.name(),
            registerRequest.role(),
            registerRequest.tagIds(),
            registerRequest.level()
        );
    }

}
