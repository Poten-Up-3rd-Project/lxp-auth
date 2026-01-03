package com.lxp.auth.infrastructure.web.external.config;

import com.lxp.auth.infrastructure.web.external.client.UserServiceFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {UserServiceFeignClient.class})
public class FeignConfig {
}
