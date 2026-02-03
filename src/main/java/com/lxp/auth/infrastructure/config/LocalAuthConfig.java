package com.lxp.auth.infrastructure.config;

import com.lxp.auth.infrastructure.security.jwt.config.JwtConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = {JwtConfig.class})
public class LocalAuthConfig {
}
