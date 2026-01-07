package com.lxp.auth.application.port.provided.usecase;

import com.lxp.auth.application.port.provided.command.LocalAuthRegisterCommand;
import com.lxp.common.application.port.in.CommandUseCase;

@FunctionalInterface
public interface LocalAuthRegisterUseCase extends CommandUseCase<LocalAuthRegisterCommand> {
}
