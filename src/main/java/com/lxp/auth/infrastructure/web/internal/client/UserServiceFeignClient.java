package com.lxp.auth.infrastructure.web.internal.client;

import com.lxp.auth.infrastructure.web.internal.client.dto.CreateUserRequest;
import com.lxp.auth.infrastructure.web.internal.client.dto.UserRoleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userService", url = "${services.user-service.url}")
public interface UserServiceFeignClient {

    @PostMapping("/internal/api-v1/users")
    ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request);

    @GetMapping("/internal/api-v1/users/{userId}/role")
    ResponseEntity<UserRoleResponse> getRole(@PathVariable String userId);
}
