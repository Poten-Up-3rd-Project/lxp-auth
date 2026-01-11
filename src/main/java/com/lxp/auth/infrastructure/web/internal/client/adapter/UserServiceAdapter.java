package com.lxp.auth.infrastructure.web.internal.client.adapter;

import com.lxp.auth.application.port.required.UserServicePort;
import com.lxp.auth.application.port.required.command.CreateUserCommand;
import com.lxp.auth.application.port.required.query.UserRoleQuery;
import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.infrastructure.web.internal.client.UserServiceFeignClient;
import com.lxp.auth.infrastructure.web.internal.client.dto.UserRoleResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserRoleFallback")
    public UserRoleQuery getUserRole(UserId userId) {
        UserRoleResponse response = unwrapResponse(userServiceFeignClient.getRole(userId.asString()));
        return new UserRoleQuery(response.role(), response.status(), response.deletedAt());
    }

    private void createUserFallback(CreateUserCommand command, Throwable t) {
        log.warn("CircuitBreaker fallback - createUser: userId={}", command.userId(), t);
        throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR, "User service is temporarily unavailable", t);
    }

    private UserRoleQuery getUserRoleFallback(UserId userId, Throwable t) {
        log.warn("CircuitBreaker fallback - getUserInfo: userId={}", userId, t);
        throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR, "User service is temporarily unavailable", t);
    }

    private <T> T unwrapResponse(ResponseEntity<T> response) {
        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody() || response.getBody() == null) {
            throw new AuthException(AuthErrorCode.EXTERNAL_SERVICE_ERROR);
        }

        return response.getBody();
    }
}
