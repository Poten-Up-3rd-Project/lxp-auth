package com.lxp.auth.application.service;

import com.lxp.auth.application.port.provided.command.LocalAuthLoginCommand;
import com.lxp.auth.application.port.provided.dto.LoginResult;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLoginUseCase;
import com.lxp.auth.application.port.required.AuthQueryPort;
import com.lxp.auth.application.port.required.UserServicePort;
import com.lxp.auth.application.port.required.query.AuthView;
import com.lxp.auth.application.port.required.query.UserRoleQuery;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.EmailNotFoundException;
import com.lxp.auth.domain.common.exception.LoginFailureException;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.auth.domain.common.model.vo.TokenClaims;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.domain.local.service.LocalAuthService;
import com.lxp.auth.domain.local.service.spec.UserLoginSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalAuthLoginService implements LocalAuthLoginUseCase {

    private final LocalAuthService localAuthService;
    private final AuthQueryPort authQueryPort;
    private final UserServicePort userServicePort;
    private final JwtPolicy jwtPolicy;

    @Override
    public LoginResult execute(LocalAuthLoginCommand command) {
        AuthView authView = authQueryPort.findByLoginIdentifier(command.email())
            .orElseThrow(EmailNotFoundException::new);

        localAuthService.authenticate(new UserLoginSpec(
            command.email(), command.password(), authView.hashedPassword())
        );
        UserRoleQuery query = userServicePort.getUserRole(authView.userId());

        if (query.status().equals("DELETED")) {
            throw new LoginFailureException(AuthErrorCode.ACCOUNT_DEACTIVATED);
        }

        AuthTokenInfo token = jwtPolicy.createToken(
            new TokenClaims(authView.userId().asString(), authView.loginIdentifier(), List.of(query.role()))
        );
        return new LoginResult(token.accessToken(), token.expiresIn(), authView.userId().asString());
    }
}
