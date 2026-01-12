package com.lxp.auth.infrastructure.web.external.passport.filter;

import com.lxp.auth.infrastructure.web.external.passport.model.PassportClaims;
import com.lxp.auth.infrastructure.web.external.passport.support.PassportExtractor;
import com.lxp.auth.infrastructure.web.external.passport.support.PassportVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PassportAuthenticationFilter extends OncePerRequestFilter {

    private final PassportExtractor extractor;
    private final PassportVerifier verifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String encodedPassport = extractor.extract(request);
        log.info("encodedPassport={}", encodedPassport);

        if (encodedPassport != null) {
            try {
                PassportClaims claims = verifier.verify(encodedPassport);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.userId(),
                    encodedPassport,
                    claims.roles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                MDC.put("traceId", claims.traceId());

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid passport", e);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api-v1/auth/register")
            || path.startsWith("/api-v1/auth/login")
            || path.startsWith("/actuator/");
    }
}
