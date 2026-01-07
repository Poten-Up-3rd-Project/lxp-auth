package com.lxp.auth.domain.common.support;

import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;

import java.util.List;
import java.util.Objects;

public class AuthGuard {

    public static <T> T requireNonNull(T obj, String message) {
        if (Objects.isNull(obj)) {
            throw missing(message);
        }
        return obj;
    }

    public static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw missing(message);
        }
        return value;
    }

    public static <T> List<T> requireNonEmpty(List<T> list, String message) {
        if (list == null || list.isEmpty()) {
            throw missing(message);
        }
        return list;
    }

    private static AuthException missing(String message) {
        return new AuthException(AuthErrorCode.MISSING_REQUIRED_FIELD, message);
    }
}
