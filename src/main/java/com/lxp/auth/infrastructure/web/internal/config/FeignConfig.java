package com.lxp.auth.infrastructure.web.internal.config;

import com.lxp.auth.infrastructure.web.internal.client.UserServiceFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {UserServiceFeignClient.class})
public class FeignConfig {
}
