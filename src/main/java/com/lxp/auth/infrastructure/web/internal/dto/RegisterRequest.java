package com.lxp.auth.infrastructure.web.internal.dto;

import java.util.List;

public record RegisterRequest(
    String email,
    String password,
    String name,
    String role,
    List<Long> tagIds,
    String level
) {
}
