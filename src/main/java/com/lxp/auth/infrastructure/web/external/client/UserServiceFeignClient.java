package com.lxp.auth.infrastructure.web.external.client;

import com.lxp.auth.infrastructure.web.external.client.dto.CreateUserRequest;
import com.lxp.auth.infrastructure.web.external.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userService", url = "${services.user-service.url}")
public interface UserServiceFeignClient {

    @PostMapping("/api-v1/internal/users/users")
    ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request);

    @GetMapping("/internal/api-v1/users")
    ResponseEntity<UserResponse> getUserInfo();

}
