package com.lxp.auth.application.service;

import com.lxp.auth.application.port.provided.command.LocalAuthLogoutCommand;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLogoutUseCase;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.domain.common.policy.TokenRevocationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalAuthLogoutService implements LocalAuthLogoutUseCase {

    private final JwtPolicy jwtPolicy;
    private final TokenRevocationPolicy tokenRevocationPolicy;

    @Override
    public void execute(LocalAuthLogoutCommand command) {
        String token = command.token();
        long remainingSeconds = jwtPolicy.getRemainingSeconds(token);
        if (remainingSeconds > 0) {
            tokenRevocationPolicy.revokeAccessToken(token, remainingSeconds);
        }
    }
}
