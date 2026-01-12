package com.lxp.auth.infrastructure.web.external.passport.support;

import com.lxp.auth.infrastructure.constants.PassportConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class PassportExtractor {

    public String extract(HttpServletRequest request) {
        return request.getHeader(PassportConstants.PASSPORT_HEADER_NAME);
    }
}
