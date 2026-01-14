package com.lxp.auth.infrastructure.web.external.passport.config;

import com.lxp.auth.domain.common.support.AuthGuard;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Setter
@Configuration
@ConfigurationProperties(prefix = "passport.key")
public class KeyProperties {

    private String secretKey;

    @Bean
    public SecretKey passportSecretKey() {
        AuthGuard.requireNonBlank(secretKey, "jwt secret key cannot be null or empty");
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}
