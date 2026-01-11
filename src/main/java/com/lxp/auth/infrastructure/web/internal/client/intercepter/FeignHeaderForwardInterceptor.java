package com.lxp.auth.infrastructure.web.internal.client.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignHeaderForwardInterceptor implements RequestInterceptor {

    @Value("${internal.auth-token}")
    private String internalAuthToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + internalAuthToken);
    }
}
