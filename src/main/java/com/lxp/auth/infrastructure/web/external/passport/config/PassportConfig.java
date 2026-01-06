package com.lxp.auth.infrastructure.web.external.passport.config;

import com.lxp.auth.infrastructure.web.external.passport.filter.PassportAuthenticationEntryPoint;
import com.lxp.auth.infrastructure.web.external.passport.filter.PassportAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class PassportConfig {

    private final PassportAuthenticationEntryPoint passportAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PassportAuthenticationFilter passportFilter) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(configurer ->
                configurer.authenticationEntryPoint(passportAuthenticationEntryPoint)
            )
            .addFilterBefore(passportFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .build();
    }

}
