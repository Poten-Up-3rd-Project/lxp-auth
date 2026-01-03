package com.lxp.auth.infrastructure.persistence.local.read.adapter;

import com.lxp.auth.application.port.out.AuthQueryPort;
import com.lxp.auth.application.port.out.query.AuthView;
import com.lxp.auth.infrastructure.persistence.local.read.repository.LocalAuthReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocalAuthReadAdapter implements AuthQueryPort {

    private final LocalAuthReadRepository localAuthReadRepository;
    private final LocalAuthReadMapper localAuthReadMapper;

    @Override
    public Optional<AuthView> findByLoginIdentifier(String loginIdentifier) {
        return localAuthReadRepository.findByLoginIdentifier(loginIdentifier).map(localAuthReadMapper::toView);
    }

    @Override
    public Optional<AuthView> findByUserId(String userId) {
        return localAuthReadRepository.findByIdWithProjection(userId).map(localAuthReadMapper::toView);
    }
}
