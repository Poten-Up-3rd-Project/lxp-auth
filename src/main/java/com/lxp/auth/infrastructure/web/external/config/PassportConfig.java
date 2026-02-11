package com.lxp.auth.infrastructure.web.external.config;

import com.lxp.passport.bean.filter.PassportFilter;
import com.lxp.passport.security.filter.PassportAuthenticationEntryPoint;
import com.lxp.passport.security.filter.PassportAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

@Configuration
@RequiredArgsConstructor
public class PassportConfig {

    private final PassportFilter passportFilter;
    private final PassportAuthenticationFilter passportAuthenticationFilter;
    private final PassportAuthenticationEntryPoint passportAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .requestCache(RequestCacheConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityContext(c -> c.securityContextRepository(new NullSecurityContextRepository()))
            .logout(AbstractHttpConfigurer::disable)
            .exceptionHandling(configurer ->
                configurer.authenticationEntryPoint(passportAuthenticationEntryPoint)
            )
            .addFilterBefore(passportFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(passportAuthenticationFilter, PassportFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api-v1/auth/register", "/api-v1/auth/login", "/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }

}
