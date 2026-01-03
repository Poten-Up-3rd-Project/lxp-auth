package com.lxp.auth.application.service;

import com.lxp.auth.application.port.in.command.LocalAuthLogoutCommand;
import com.lxp.auth.application.port.in.usecase.LocalAuthLogoutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalAuthLogoutService implements LocalAuthLogoutUseCase {

    @Override
    public Void execute(LocalAuthLogoutCommand command) {
        return null;
    }
}
