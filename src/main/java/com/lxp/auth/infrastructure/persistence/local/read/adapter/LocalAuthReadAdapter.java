package com.lxp.auth.infrastructure.persistence.local.read.adapter;

import com.lxp.auth.application.port.required.AuthQueryPort;
import com.lxp.auth.application.port.required.query.AuthView;
import com.lxp.auth.infrastructure.persistence.local.read.repository.LocalAuthJpaReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocalAuthReadAdapter implements AuthQueryPort {

    private final LocalAuthJpaReadRepository localAuthJpaReadRepository;
    private final LocalAuthReadMapper localAuthReadMapper;

    @Override
    public Optional<AuthView> findByLoginIdentifier(String loginIdentifier) {
        return localAuthJpaReadRepository.findByLoginIdentifier(loginIdentifier).map(localAuthReadMapper::toView);
    }

    @Override
    public Optional<AuthView> findByUserId(String userId) {
        return localAuthJpaReadRepository.findByIdWithProjection(userId).map(localAuthReadMapper::toView);
    }
}
