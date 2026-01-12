package com.lxp.auth.infrastructure.web.external.controller;

import com.lxp.auth.application.port.provided.command.LocalAuthLoginCommand;
import com.lxp.auth.application.port.provided.command.LocalAuthRegisterCommand;
import com.lxp.auth.infrastructure.web.external.controller.dto.LoginRequest;
import com.lxp.auth.infrastructure.web.external.controller.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthExternalMapper {

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
