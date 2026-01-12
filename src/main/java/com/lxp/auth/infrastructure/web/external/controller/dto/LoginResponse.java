package com.lxp.auth.infrastructure.web.external.controller.dto;

public record LoginResponse(
    String userId,
    String role
) {
}
