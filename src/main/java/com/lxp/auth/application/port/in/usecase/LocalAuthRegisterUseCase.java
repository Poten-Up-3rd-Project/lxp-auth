package com.lxp.auth.application.port.in.usecase;

import com.lxp.auth.application.port.in.command.LocalAuthRegisterCommand;
import com.lxp.common.application.port.in.CommandUseCase;

@FunctionalInterface
public interface LocalAuthRegisterUseCase extends CommandUseCase<LocalAuthRegisterCommand> {
}
