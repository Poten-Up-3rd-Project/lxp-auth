package com.lxp.auth.infrastructure.persistence.local.read.adapter;

import com.lxp.auth.application.port.required.query.AuthView;
import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.domain.local.model.vo.HashedPassword;
import com.lxp.auth.infrastructure.persistence.local.read.dto.LocalAuthProjection;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthReadMapper {

    public AuthView toView(LocalAuthProjection projection) {
        return new AuthView(
            UserId.of(projection.getId()),
            projection.getLoginIdentifier(),
            new HashedPassword(projection.getHashedPassword())
        );
    }

}
