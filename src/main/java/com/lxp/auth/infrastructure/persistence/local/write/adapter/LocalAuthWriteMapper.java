package com.lxp.auth.infrastructure.persistence.local.write.adapter;

import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.infrastructure.persistence.local.write.entity.LocalAuthJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthWriteMapper {

    public LocalAuthJpaEntity toEntity(LocalAuth localAuth) {
        return LocalAuthJpaEntity.of(
            localAuth.userId().value(),
            localAuth.loginIdentifier(),
            localAuth.hashedPasswordAsString()
        );
    }
}
