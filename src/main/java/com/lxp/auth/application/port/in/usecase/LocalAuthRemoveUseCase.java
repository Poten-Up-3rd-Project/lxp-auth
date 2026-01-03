package com.lxp.auth.application.port.in.usecase;

import com.lxp.auth.application.port.in.command.LocalAuthRemoveCommand;
import com.lxp.common.application.port.in.CommandWithResultUseCase;

public interface LocalAuthRemoveUseCase extends CommandWithResultUseCase<LocalAuthRemoveCommand, Void> {
}
