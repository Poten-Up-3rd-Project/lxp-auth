package com.lxp.auth.infrastructure.security.jwt;

import com.lxp.auth.domain.common.exception.InvalidTokenException;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.auth.domain.common.model.vo.TokenClaims;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.infrastructure.security.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider implements JwtPolicy {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final long accessTokenValidityInMilliseconds;

    public JwtTokenProvider(JwtConfig jwtConfig) throws Exception {
        this.publicKey = jwtConfig.getPublicKey();
        this.privateKey = jwtConfig.getPrivateKey();
        this.accessTokenValidityInMilliseconds = jwtConfig.getAccessTokenValidityMillis();
    }

    @Override
    public AuthTokenInfo createToken(TokenClaims claims) {
        log.info("Creating token - userId: {}, email: {}, authorities: {}",
            claims.userId(), claims.email(), claims.authorities());

        long now = (new Date()).getTime();
        long expirationMillis = now + accessTokenValidityInMilliseconds;
        Date validity = new Date(expirationMillis);

        long expiresInSeconds = accessTokenValidityInMilliseconds / 1000;

        String authorities = String.join(",", claims.authorities());

        String token = Jwts.builder()
            .subject(claims.email())
            .claim("userId", claims.userId())
            .claim("auth", authorities)
            .expiration(validity)
            .signWith(privateKey)
            .compact();

        log.info("Token created successfully. First 20 chars: {}...", token.substring(0, 20));
        return new AuthTokenInfo(token, expiresInSeconds);
    }

    @Override
    public TokenClaims parseToken(String token) {
        Claims claims = parseClaims(token);
        return new TokenClaims(
            claims.get("userId").toString(),
            claims.getSubject(),
            Arrays.asList(claims.get("auth").toString().split(","))
        );
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Signature", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    @Override
    public long getExpirationTimeMillis(String token) {
        return parseClaims(token).getExpiration().getTime();
    }

    @Override
    public long getRemainingSeconds(String token) {
        long expirationTimeMillis = getExpirationTimeMillis(token);
        long nowMillis = System.currentTimeMillis();

        if (expirationTimeMillis <= nowMillis) {
            return 0;
        }

        return (expirationTimeMillis - nowMillis) / 1000;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료 시간 계산을 위한 Claims 반환
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 서명 오류, 토큰 형식 오류 등은 예외 처리
            throw new InvalidTokenException("Invalid JWT signature or format.");
        } catch (Exception e) {
            return null;
        }
    }
}
