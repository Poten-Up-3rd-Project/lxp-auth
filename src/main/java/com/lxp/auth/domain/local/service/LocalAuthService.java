package com.lxp.auth.domain.local.service;

import com.lxp.auth.domain.common.exception.AuthErrorCode;
import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.domain.common.exception.LoginFailureException;
import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.domain.local.model.vo.HashedPassword;
import com.lxp.auth.domain.local.policy.PasswordPolicy;
import com.lxp.auth.domain.local.service.spec.AuthCreateSpec;
import com.lxp.auth.domain.local.service.spec.UserLoginSpec;
import com.lxp.common.domain.annotation.DomainService;

import java.util.Objects;

import static com.lxp.auth.domain.common.exception.AuthErrorCode.MISSING_REQUIRED_FIELD;

@DomainService
public class LocalAuthService {

    private final PasswordPolicy passwordPolicy;

    public LocalAuthService(PasswordPolicy passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
    }

    public LocalAuth create(AuthCreateSpec spec) {
        if (Objects.isNull(spec)) {
            throw new AuthException(MISSING_REQUIRED_FIELD, "사용자 필수 정보가 없습니다.");
        }

        HashedPassword hashedPassword = passwordPolicy.apply(spec.password());
        return LocalAuth.register(spec.email(), hashedPassword);
    }

    public void authenticate(UserLoginSpec spec) {
        if (Objects.isNull(spec)) {
            throw new AuthException(MISSING_REQUIRED_FIELD);
        }
        if (!passwordPolicy.isMatch(spec.password(), spec.hashedPassword())) {
            throw new LoginFailureException(AuthErrorCode.INVALID_CREDENTIALS);
        }
    }

}
