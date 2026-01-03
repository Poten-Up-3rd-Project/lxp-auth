package com.lxp.auth.application.port.in.usecase;

import com.lxp.auth.application.port.in.command.LocalAuthLoginCommand;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.common.application.port.in.CommandWithResultUseCase;

@FunctionalInterface
public interface LocalAuthLoginUseCase extends CommandWithResultUseCase<LocalAuthLoginCommand, AuthTokenInfo> {
}
