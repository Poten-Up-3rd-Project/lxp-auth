package com.lxp.auth.infrastructure.security.adapter;

import com.lxp.auth.domain.common.model.vo.TokenClaims;
import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.infrastructure.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter {

    private final JwtPolicy jwtPolicy;

    /**
     * JWT 토큰을 Spring Security Authentication으로 변환합니다.
     */
    public Authentication convert(String token) {
        TokenClaims claims = jwtPolicy.parseToken(token);

        Collection<? extends GrantedAuthority> authorities =
            claims.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails(
            claims.userId(),
            claims.email(),
            claims.email(),
            "",
            authorities
        );

        return new UsernamePasswordAuthenticationToken(
            principal, "", authorities
        );
    }

}
