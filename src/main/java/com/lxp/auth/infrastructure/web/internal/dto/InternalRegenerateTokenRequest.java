package com.lxp.auth.infrastructure.web.internal.dto;

import java.util.List;

public record InternalRegenerateTokenRequest(
    String email,
    String token,
    List<String> roles
) {
}
