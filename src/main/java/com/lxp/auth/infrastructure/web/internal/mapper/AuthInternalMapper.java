package com.lxp.auth.infrastructure.web.internal.mapper;

import com.lxp.auth.application.port.provided.command.TokenRegeneratedCommand;
import com.lxp.auth.application.port.provided.dto.AuthTokenResult;
import com.lxp.auth.infrastructure.web.internal.dto.InternalRegenerateTokenRequest;
import com.lxp.auth.infrastructure.web.internal.dto.InternalTokenResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthInternalMapper {

    public TokenRegeneratedCommand toTokenRegeneratedCommand(
        InternalRegenerateTokenRequest request, String userId
    ) {
        return new TokenRegeneratedCommand(userId, request.roles(), request.email(), request.token());
    }

    public InternalTokenResponse toInternalTokenResponse(AuthTokenResult result) {
        return new InternalTokenResponse(result.accessToken(), result.expiresIn());
    }
}
