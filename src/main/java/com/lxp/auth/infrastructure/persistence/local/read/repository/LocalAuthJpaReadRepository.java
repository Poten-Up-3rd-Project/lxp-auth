package com.lxp.auth.infrastructure.persistence.local.read.repository;

import com.lxp.auth.infrastructure.persistence.local.read.dto.LocalAuthProjection;
import com.lxp.auth.infrastructure.persistence.local.write.entity.LocalAuthJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalAuthJpaReadRepository extends JpaRepository<LocalAuthJpaEntity, String> {

    @Query(value = """
        SELECT a.id as id, a.loginIdentifier as loginIndentifier, a.hashedPassword as hashedPassword
        FROM LocalAuthJpaEntity a
        WHERE a.loginIdentifier = :loginIdentifier
        """)
    Optional<LocalAuthProjection> findByLoginIdentifier(@Param("loginIdentifier") String loginIdentifier);

    @Query(value = """
        SELECT a.id as id, a.loginIdentifier as loginIndentifier, a.hashedPassword as hashedPassword
        FROM LocalAuthJpaEntity a
        WHERE a.id = :id
        """)
    Optional<LocalAuthProjection> findByIdWithProjection(@Param("id") String id);
}
