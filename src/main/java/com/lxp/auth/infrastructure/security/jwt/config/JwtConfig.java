package com.lxp.auth.infrastructure.security.jwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private final String publicKeyString;
    private final String privateKeyString;
    private final long accessTokenValidityInSeconds;

    public JwtConfig(String publicKeyString, String privateKeyString, long accessTokenValidityInSeconds) {
        this.publicKeyString = publicKeyString;
        this.privateKeyString = privateKeyString;
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }

    public PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public long getAccessTokenValidityMillis() {
        log.info("accessTokenValidityInSeconds={}", accessTokenValidityInSeconds);
        return accessTokenValidityInSeconds * 1000;
    }
}

