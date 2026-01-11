package com.lxp.auth.application.port.provided.usecase;

import com.lxp.auth.application.port.provided.command.LocalAuthLoginCommand;
import com.lxp.auth.application.port.provided.dto.LoginResult;
import com.lxp.common.application.port.in.CommandWithResultUseCase;

@FunctionalInterface
public interface LocalAuthLoginUseCase extends CommandWithResultUseCase<LocalAuthLoginCommand, LoginResult> {
}
