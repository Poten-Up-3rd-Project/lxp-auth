package com.lxp.auth.application.out;

import com.lxp.auth.domain.local.model.entity.LocalAuth;

public interface AuthCommandPort {

    void save(LocalAuth localAuth);

    void remove(LocalAuth localAuth);
}
