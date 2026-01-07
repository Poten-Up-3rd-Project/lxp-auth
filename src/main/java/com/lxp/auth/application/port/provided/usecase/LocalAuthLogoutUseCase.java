package com.lxp.auth.application.port.provided.usecase;

import com.lxp.auth.application.port.provided.command.LocalAuthLogoutCommand;
import com.lxp.common.application.port.in.CommandUseCase;

@FunctionalInterface
public interface LocalAuthLogoutUseCase extends CommandUseCase<LocalAuthLogoutCommand> {
}
