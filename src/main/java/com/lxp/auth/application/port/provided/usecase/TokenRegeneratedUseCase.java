package com.lxp.auth.application.port.provided.usecase;

import com.lxp.auth.application.port.provided.command.TokenRegeneratedCommand;
import com.lxp.auth.application.port.provided.dto.AuthTokenResult;
import com.lxp.common.application.port.in.CommandWithResultUseCase;

@FunctionalInterface
public interface TokenRegeneratedUseCase extends CommandWithResultUseCase<TokenRegeneratedCommand, AuthTokenResult> {
}
