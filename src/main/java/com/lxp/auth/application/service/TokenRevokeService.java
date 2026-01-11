package com.lxp.auth.application.service;

import com.lxp.auth.application.port.provided.command.TokenRevokeCommand;
import com.lxp.auth.application.port.provided.usecase.TokenRevokeUseCase;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.domain.common.policy.TokenRevocationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRevokeService implements TokenRevokeUseCase {

    private final TokenRevocationPolicy tokenRevocationPolicy;
    private final JwtPolicy jwtPolicy;

    @Override
    public void execute(TokenRevokeCommand command) {
        long remainingSeconds = jwtPolicy.getRemainingSeconds(command.token());

        if (remainingSeconds > 0) {
            tokenRevocationPolicy.revokeAccessToken(command.token(), remainingSeconds);
        }
    }
}
