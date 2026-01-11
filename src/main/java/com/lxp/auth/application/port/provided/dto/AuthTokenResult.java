package com.lxp.auth.application.port.provided.dto;

public record AuthTokenResult(String accessToken, long expiresIn) {
}
