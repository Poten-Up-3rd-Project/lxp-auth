package com.lxp.auth.infrastructure.security.filter;

import com.lxp.auth.domain.common.policy.JwtPolicy;
import com.lxp.auth.domain.common.policy.TokenRevocationPolicy;
import com.lxp.auth.infrastructure.security.adapter.AuthHeaderResolver;
import com.lxp.auth.infrastructure.security.adapter.JwtAuthenticationConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthHeaderResolver authHeaderResolver;
    private final JwtPolicy jwtPolicy;
    private final TokenRevocationPolicy revocationPolicy;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = authHeaderResolver.resolveToken(request);

        if (token != null && jwtPolicy.validateToken(token)) {
            if (revocationPolicy.isTokenBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token has been logged out and revoked.");
                return;
            }
            Authentication authentication = jwtAuthenticationConverter.convert(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
