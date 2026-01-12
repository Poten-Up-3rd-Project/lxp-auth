package com.lxp.auth.infrastructure.web.external.controller.dto;

public record LoginRequest(
    String email, String password
) {
}
