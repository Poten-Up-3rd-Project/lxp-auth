package com.lxp.auth.infrastructure.config;

import com.lxp.auth.infrastructure.web.external.passport.config.KeyProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = {KeyProperties.class})
public class LocalAuthConfig {
}
