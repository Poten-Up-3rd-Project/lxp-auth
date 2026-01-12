package com.lxp.auth.infrastructure.security.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SessionDebugConfig {
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> httpSessionLogger() {
        return new ServletListenerRegistrationBean<>(new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                log.info("[SESSION CREATED] id={}", se.getSession().getId());
                // 원하면 스택도 찍기
                for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
                    log.info("  at {}", e);
                }
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                log.info("[SESSION DESTROYED] id={}", se.getSession().getId());
            }
        });
    }
}
