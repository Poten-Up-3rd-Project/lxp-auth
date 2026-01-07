package com.lxp.auth.infrastructure.security.jwt.config;

import com.lxp.auth.domain.common.support.AuthGuard;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtConfig {

    private String secretKey;
    private long accessTokenValiditySeconds;

    @Bean
    public SecretKey jwtSecretKey() {
        AuthGuard.requireNonBlank(secretKey, "jwt secret key cannot be null or empty");
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public long getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds * 1000;
    }
}

