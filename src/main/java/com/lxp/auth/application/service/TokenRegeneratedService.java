package com.lxp.auth.application.service;

import com.lxp.auth.application.port.provided.command.TokenRegeneratedCommand;
import com.lxp.auth.application.port.provided.dto.AuthTokenResult;
import com.lxp.auth.application.port.provided.usecase.TokenRegeneratedUseCase;
import com.lxp.auth.application.service.mapper.LocalAuthServiceMapper;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.domain.common.policy.TokenRevocationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRegeneratedService implements TokenRegeneratedUseCase {

    private final LocalAuthServiceMapper authServiceMapper;
    private final TokenRevocationPolicy tokenRevocationPolicy;
    private final JwtPolicy jwtPolicy;

    @Override
    public AuthTokenResult execute(TokenRegeneratedCommand command) {
        AuthTokenInfo token = jwtPolicy.createToken(authServiceMapper.toTokenClaims(command));

        long remainingSeconds = jwtPolicy.getRemainingSeconds(command.token());
        if (remainingSeconds > 0) {
            tokenRevocationPolicy.revokeAccessToken(command.token(), remainingSeconds);
        }
        return authServiceMapper.toAuthTokenResult(token);
    }
}
