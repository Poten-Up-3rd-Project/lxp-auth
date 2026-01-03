package com.lxp.auth.infrastructure.web.internal.dto;

public record LoginRequest(
    String email, String password
) {
}
