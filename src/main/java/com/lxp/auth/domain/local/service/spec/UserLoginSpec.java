package com.lxp.auth.domain.local.service.spec;

import com.lxp.auth.domain.local.model.vo.HashedPassword;

public record UserLoginSpec(
    String email,
    String password,
    HashedPassword hashedPassword
) {
}
