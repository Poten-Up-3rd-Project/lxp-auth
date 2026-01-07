package com.lxp.auth.application.port.required.query;

import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.domain.local.model.vo.HashedPassword;

public record AuthView(
    UserId userId,
    String loginIdentifier,
    HashedPassword hashedPassword
) {
}
