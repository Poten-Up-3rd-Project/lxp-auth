package com.lxp.auth.application.service.mapper;

import com.lxp.auth.application.port.provided.command.LocalAuthRegisterCommand;
import com.lxp.auth.application.port.provided.command.TokenRegeneratedCommand;
import com.lxp.auth.application.port.provided.dto.AuthTokenResult;
import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.auth.domain.common.model.vo.TokenClaims;
import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.domain.local.service.spec.AuthCreateSpec;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthServiceMapper {

    public AuthCreateSpec toAuthCreateSpec(LocalAuthRegisterCommand command) {
        return new AuthCreateSpec(
            command.email(), command.password()
        );
    }

    public CreateUserCommand toCreateUserCommand(LocalAuthRegisterCommand command, UserId userId) {
        return new CreateUserCommand(
            userId.asString(),
            command.name(),
            command.email(),
            command.role(),
            command.tagIds(),
            command.level()
        );
    }

    public TokenClaims toTokenClaims(TokenRegeneratedCommand command) {
        return new TokenClaims(
            command.userId(),
            command.email(),
            command.role()
        );
    }

    public AuthTokenResult toAuthTokenResult(AuthTokenInfo tokenInfo) {
        return new AuthTokenResult(
            tokenInfo.accessToken(),
            tokenInfo.expiresIn()
        );
    }

}
