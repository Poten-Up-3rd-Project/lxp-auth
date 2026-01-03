package com.lxp.auth.application.port.out;

import com.lxp.auth.application.port.out.query.AuthView;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<AuthView> findByLoginIdentifier(String loginIdentifier);

    Optional<AuthView> findByUserId(String userId);

}
