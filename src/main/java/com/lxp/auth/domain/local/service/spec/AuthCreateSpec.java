package com.lxp.auth.domain.local.service.spec;

public record AuthCreateSpec(
    String email,
    String password
) {
}
