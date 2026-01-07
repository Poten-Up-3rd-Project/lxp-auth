package com.lxp.auth.infrastructure.web.external.adapter;

import com.lxp.auth.application.port.required.UserServicePort;
import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.application.port.required.query.UserInfo;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.infrastructure.web.external.client.UserServiceFeignClient;
import com.lxp.auth.infrastructure.web.external.dto.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "userService", fallbackMethod = "createUserFallback")
    public void createUser(CreateUserCommand command) {
        userServiceFeignClient.createUser(userServiceMapper.createUserRequest(command));
        log.info("Successfully created user in user-service: userId={}", command.userId());
    }

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserInfoFallback")
    public UserInfo getUserInfo(String userId) {
        UserResponse response = userServiceFeignClient.getUserInfo(userId);
        return userServiceMapper.toUserInfo(response);
    }

    private void createUserFallback(CreateUserCommand command, Throwable t) {
        log.warn("CircuitBreaker fallback - createUser: userId={}", command.userId(), t);
        throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR, "User service is temporarily unavailable", t);
    }

    private UserInfo getUserInfoFallback(String userId, Throwable t) {
        log.warn("CircuitBreaker fallback - getUserInfo: userId={}", userId, t);
        throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR, "User service is temporarily unavailable", t);
    }
}
