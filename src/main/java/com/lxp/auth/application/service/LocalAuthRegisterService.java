package com.lxp.auth.application.service;

import com.lxp.auth.application.port.provided.command.LocalAuthRegisterCommand;
import com.lxp.auth.application.port.provided.usecase.LocalAuthRegisterUseCase;
import com.lxp.auth.application.port.required.AuthCommandPort;
import com.lxp.auth.application.port.required.UserServicePort;
import com.lxp.auth.application.service.mapper.LocalAuthSpecMapper;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.domain.local.service.LocalAuthService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocalAuthRegisterService implements LocalAuthRegisterUseCase {

    private final AuthCommandPort authCommandPort;
    private final LocalAuthService localAuthService;
    private final UserServicePort userServicePort;
    private final LocalAuthSpecMapper localAuthSpecMapper;

    @Override
    public void execute(LocalAuthRegisterCommand command) {
        LocalAuth localAuth = localAuthService.create(localAuthSpecMapper.toAuthCreateSpec(command));
        authCommandPort.save(localAuth);

        try {
            userServicePort.createUser(localAuthSpecMapper.toCreateUserCommand(command, localAuth.getId()));
        } catch (FeignException.Conflict e) {
            throw new AuthException(AuthErrorCode.USER_CREATION_FAILED, "User already exists", e);
        } catch (FeignException e) {
            throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR, "Failed to create user", e);
        }

    }
}
