package com.lxp.auth.infrastructure.web.internal.client.dto;

import java.util.List;

public record CreateUserRequest(
    String userId,
    String name,
    String email,
    String role,
    List<Long> tagIds,
    String level
) {
}
