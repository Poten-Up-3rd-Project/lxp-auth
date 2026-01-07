package com.lxp.auth.domain.local.model.vo;

import com.lxp.auth.domain.common.support.AuthGuard;

public record HashedPassword(String value) {

    public HashedPassword {
        AuthGuard.requireNonBlank(value, "HashedPassword value cannot be empty.");
    }

}
