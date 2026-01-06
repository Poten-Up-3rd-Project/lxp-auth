package com.lxp.auth.application.port.in.usecase;

import com.lxp.auth.application.port.in.command.LocalAuthLogoutCommand;
import com.lxp.common.application.port.in.CommandUseCase;

@FunctionalInterface
public interface LocalAuthLogoutUseCase extends CommandUseCase<LocalAuthLogoutCommand> {
}
