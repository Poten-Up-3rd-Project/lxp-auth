package com.lxp.auth.domain.local.model.vo;

import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;

import java.util.Objects;

public record HashedPassword(String value) {

    public HashedPassword {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new AuthException(AuthErrorCode.MISSING_REQUIRED_FIELD, "HashedPassword value cannot be empty.");
        }
    }

}
