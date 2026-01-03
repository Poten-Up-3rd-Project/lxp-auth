package com.lxp.auth.application.service;

import com.lxp.auth.application.port.in.command.LocalAuthLoginCommand;
import com.lxp.auth.application.port.in.usecase.LocalAuthLoginUseCase;
import com.lxp.auth.application.port.out.AuthQueryPort;
import com.lxp.auth.application.port.out.UserServicePort;
import com.lxp.auth.application.port.out.query.UserInfo;
import com.lxp.auth.application.port.out.query.AuthView;
import com.lxp.auth.domain.common.exception.EmailNotFoundException;
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
    public AuthTokenInfo execute(LocalAuthLoginCommand command) {
        AuthView authView = authQueryPort.findByLoginIdentifier(command.email())
            .orElseThrow(EmailNotFoundException::new);

        localAuthService.authenticate(new UserLoginSpec(
            command.email(), command.password(), authView.hashedPassword())
        );
        UserInfo userInfo = userServicePort.getUserInfo(authView.userId().asString());

        return jwtPolicy.createToken(
            new TokenClaims(authView.userId().asString(), authView.loginIdentifier(), List.of(userInfo.role()))
        );
    }
}
