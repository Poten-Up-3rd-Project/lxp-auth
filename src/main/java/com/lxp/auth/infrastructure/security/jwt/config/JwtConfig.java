package com.lxp.auth.infrastructure.security.jwt.config;

import com.lxp.auth.domain.common.support.AuthGuard;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
@Slf4j
public class JwtConfig {

    private String secretKey;
    private long accessTokenValidityInSeconds;

    @Bean
    public SecretKey jwtSecretKey() {
        AuthGuard.requireNonBlank(secretKey, "jwt secret key cannot be null or empty");
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public long getAccessTokenValidityMillis() {
        log.info("accessTokenValidityInSeconds={}", accessTokenValidityInSeconds);
        return accessTokenValidityInSeconds * 1000;
    }
}

