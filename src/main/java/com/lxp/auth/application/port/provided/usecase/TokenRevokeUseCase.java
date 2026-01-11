package com.lxp.auth.application.port.provided.usecase;

import com.lxp.auth.application.port.provided.command.TokenRevokeCommand;
import com.lxp.common.application.port.in.CommandUseCase;

@FunctionalInterface
public interface TokenRevokeUseCase extends CommandUseCase<TokenRevokeCommand> {
}
