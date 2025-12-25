package com.lxp.auth.application;

import com.lxp.auth.application.in.command.LocalAuthRemoveCommand;
import com.lxp.auth.application.in.usecase.LocalAuthRemoveUseCase;
import org.springframework.stereotype.Service;

@Service
public class LocalAuthRemoveService implements LocalAuthRemoveUseCase {

    @Override
    public Void execute(LocalAuthRemoveCommand command) {
        return null;
    }
}
