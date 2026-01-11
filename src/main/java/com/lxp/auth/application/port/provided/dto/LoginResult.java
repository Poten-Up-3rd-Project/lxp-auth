package com.lxp.auth.application.port.provided.dto;

public record LoginResult(
    String token,
    long expiresIn,
    String userId,
    String role
) {
}
