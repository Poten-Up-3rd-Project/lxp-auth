package com.lxp.auth.infrastructure.web.external.passport.exception;

import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;

public class InvalidPassportException extends AuthException {

    public InvalidPassportException(String message) {
        super(AuthErrorCode.UNAUTHORIZED_ACCESS, message);
    }
}
