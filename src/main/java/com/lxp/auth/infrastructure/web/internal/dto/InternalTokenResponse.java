package com.lxp.auth.infrastructure.web.internal.dto;

public record InternalTokenResponse(String accessToken, long expiresIn) {
}
