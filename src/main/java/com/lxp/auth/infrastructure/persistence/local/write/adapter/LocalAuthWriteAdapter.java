package com.lxp.auth.infrastructure.persistence.local.write.adapter;

import com.lxp.auth.application.port.out.AuthCommandPort;
import com.lxp.auth.domain.common.exception.DuplicatedEmailException;
import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.infrastructure.persistence.local.write.repository.LocalAuthWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalAuthWriteAdapter implements AuthCommandPort {

    private final LocalAuthWriteRepository localAuthWriteRepository;
    private final LocalAuthWriteMapper localAuthWriteMapper;

    @Override
    public void save(LocalAuth localAuth) {
        if (localAuthWriteRepository.existsByLoginIdentifier(localAuth.loginIdentifier())) {
            throw new DuplicatedEmailException();
        }
        localAuthWriteRepository.save(localAuthWriteMapper.toEntity(localAuth));
    }

    @Override
    public void remove(LocalAuth localAuth) {
        localAuthWriteRepository.deleteById(localAuth.getId().asString());
    }

}
