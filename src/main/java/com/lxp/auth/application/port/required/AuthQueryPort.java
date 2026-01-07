package com.lxp.auth.application.port.required;

import com.lxp.auth.application.port.required.query.AuthView;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<AuthView> findByLoginIdentifier(String loginIdentifier);

    Optional<AuthView> findByUserId(String userId);

}
