package com.lxp.auth.infrastructure.persistence.local.write.repository;

import com.lxp.auth.infrastructure.persistence.local.write.entity.LocalAuthJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalAuthJpaWriteRepository extends JpaRepository<LocalAuthJpaEntity, String> {

    boolean existsByLoginIdentifier(String loginIdentifier);

}
