package com.lxp.auth.domain.common.support;

import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;

import java.util.Objects;

public class AuthGuard {

    public static <T> T requireNonNull(T obj, String message) {
        if (Objects.isNull(obj)) {
            throw new AuthException(AuthErrorCode.MISSING_REQUIRED_FIELD, message);
        }
        return obj;
    }

}
