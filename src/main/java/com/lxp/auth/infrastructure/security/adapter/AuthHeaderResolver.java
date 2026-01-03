package com.lxp.auth.infrastructure.security.adapter;


import com.lxp.auth.domain.common.constants.CookieConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Slf4j
@Component
public class AuthHeaderResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            log.info("Cookies is null");
            return null;
        }

        return Arrays.stream(cookies)
            .filter(cookie -> CookieConstants.ACCESS_TOKEN_NAME.equals(cookie.getName()))
            .map(Cookie::getValue)
            .filter(StringUtils::hasText)
            .findFirst()
            .orElse(null);
    }

}
