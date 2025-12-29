package com.lxp.auth.application.out;

import com.lxp.auth.domain.local.model.entity.LocalAuth;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<LocalAuth> findByLoginIdentifier(String loginIdentifier);

    Optional<LocalAuth> findByUserId(String userId);

}
