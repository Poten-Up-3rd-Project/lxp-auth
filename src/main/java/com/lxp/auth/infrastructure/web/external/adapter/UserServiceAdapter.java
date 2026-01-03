package com.lxp.auth.infrastructure.web.external.adapter;

import com.lxp.auth.application.port.out.UserServicePort;
import com.lxp.auth.application.port.out.command.CreateUserCommand;
import com.lxp.auth.application.port.out.query.UserInfo;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.infrastructure.web.external.client.UserServiceFeignClient;
import com.lxp.auth.infrastructure.web.external.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {

    private final UserServiceFeignClient userServiceFeignClient;
    private final UserServiceMapper userServiceMapper;

    @Override
    public void createUser(CreateUserCommand command) {
        try {
            userServiceFeignClient.createUser(userServiceMapper.createUserRequest(command));
            log.info("Successfully created user in user-service: userId={}", command.userId());
        } catch (Exception e) {
            log.error("Failed to create user in user-service: userId={}", command.userId(), e);
            throw new AuthException(AuthErrorCode.INTERNAL_ERROR, "Failed to create user", e);
        }
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        try {
            UserResponse response = userServiceFeignClient.getUserInfo(userId);
            return userServiceMapper.toUserInfo(response);
        } catch (Exception e) {
            log.error("Failed to get user in user-service: userId={}", userId, e);
            throw new AuthException(AuthErrorCode.INTERNAL_ERROR, "Failed to get user", e);
        }
    }
}
