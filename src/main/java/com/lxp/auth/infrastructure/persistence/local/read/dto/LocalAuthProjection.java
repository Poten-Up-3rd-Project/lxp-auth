package com.lxp.auth.infrastructure.persistence.local.read.dto;

public interface LocalAuthProjection {

    String getId();

    String getLoginIdentifier();

    String getHashedPassword();

}
