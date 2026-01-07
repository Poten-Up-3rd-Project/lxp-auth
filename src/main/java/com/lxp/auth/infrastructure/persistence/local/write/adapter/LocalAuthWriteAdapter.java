package com.lxp.auth.infrastructure.persistence.local.write.adapter;

import com.lxp.auth.application.port.required.AuthCommandPort;
import com.lxp.auth.domain.common.exception.DuplicatedEmailException;
import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.infrastructure.persistence.local.write.repository.LocalAuthJpaWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalAuthWriteAdapter implements AuthCommandPort {

    private final LocalAuthJpaWriteRepository localAuthJpaWriteRepository;
    private final LocalAuthWriteMapper localAuthWriteMapper;

    @Override
    public void save(LocalAuth localAuth) {
        if (localAuthJpaWriteRepository.existsByLoginIdentifier(localAuth.loginIdentifier())) {
            throw new DuplicatedEmailException();
        }
        localAuthJpaWriteRepository.save(localAuthWriteMapper.toEntity(localAuth));
    }

    @Override
    public void remove(LocalAuth localAuth) {
        localAuthJpaWriteRepository.deleteById(localAuth.getId().asString());
    }

}
