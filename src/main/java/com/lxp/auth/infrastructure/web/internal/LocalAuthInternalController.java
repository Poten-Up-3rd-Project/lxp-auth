package com.lxp.auth.infrastructure.web.internal;

import com.lxp.auth.application.port.provided.command.LocalAuthLogoutCommand;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLoginUseCase;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLogoutUseCase;
import com.lxp.auth.application.port.provided.usecase.LocalAuthRegisterUseCase;
import com.lxp.auth.domain.common.constants.CookieConstants;
import com.lxp.auth.domain.common.model.vo.AuthTokenInfo;
import com.lxp.auth.infrastructure.web.internal.dto.LoginRequest;
import com.lxp.auth.infrastructure.web.internal.dto.RegisterRequest;
import com.lxp.auth.infrastructure.web.internal.mapper.LocalAuthInternalMapper;
import com.lxp.common.infrastructure.exception.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api-v1/auth")
@RequiredArgsConstructor
public class LocalAuthInternalController {

    private final LocalAuthLoginUseCase localAuthLoginUseCase;
    private final LocalAuthRegisterUseCase localAuthRegisterUseCase;
    private final LocalAuthLogoutUseCase localAuthLogoutUseCase;
    private final LocalAuthInternalMapper localAuthInternalMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthTokenInfo tokenInfo = localAuthLoginUseCase.execute(localAuthInternalMapper.localAuthLoginCommand(request));
        ResponseCookie cookie = createCookie(tokenInfo);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        localAuthRegisterUseCase.execute(localAuthInternalMapper.localAuthRegisterCommand(request));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@CookieValue("access_token") String accessToken,
                                                    HttpServletResponse response) {
        if (Objects.nonNull(accessToken)) {
            localAuthLogoutUseCase.execute(new LocalAuthLogoutCommand(accessToken));
            deleteCookie(response);
        }
        return ResponseEntity.ok(ApiResponse.success());
    }

    private ResponseCookie createCookie(AuthTokenInfo tokenInfo) {
        return ResponseCookie.from(CookieConstants.ACCESS_TOKEN_NAME, tokenInfo.accessToken())
            .httpOnly(CookieConstants.HTTP_ONLY)
            .secure(false)
            .path(CookieConstants.DEFAULT_PATH)
            .maxAge(tokenInfo.expiresIn())
            .sameSite("Lax")
            .build();
    }

    private void deleteCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(CookieConstants.ACCESS_TOKEN_NAME, "") // 값은 비움
            .httpOnly(CookieConstants.HTTP_ONLY)
            .secure(false)
            .path(CookieConstants.DEFAULT_PATH)
            .maxAge(0)
            .sameSite("Lax")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
