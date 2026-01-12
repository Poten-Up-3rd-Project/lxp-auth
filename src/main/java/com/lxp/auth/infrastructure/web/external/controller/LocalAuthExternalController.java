package com.lxp.auth.infrastructure.web.external.controller;

import com.lxp.auth.application.port.provided.command.LocalAuthLogoutCommand;
import com.lxp.auth.application.port.provided.dto.LoginResult;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLoginUseCase;
import com.lxp.auth.application.port.provided.usecase.LocalAuthLogoutUseCase;
import com.lxp.auth.application.port.provided.usecase.LocalAuthRegisterUseCase;
import com.lxp.auth.infrastructure.constants.CookieConstants;
import com.lxp.auth.infrastructure.web.external.controller.dto.LoginRequest;
import com.lxp.auth.infrastructure.web.external.controller.dto.LoginResponse;
import com.lxp.auth.infrastructure.web.external.controller.dto.RegisterRequest;
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
public class LocalAuthExternalController {

    private final LocalAuthLoginUseCase localAuthLoginUseCase;
    private final LocalAuthRegisterUseCase localAuthRegisterUseCase;
    private final LocalAuthLogoutUseCase localAuthLogoutUseCase;
    private final LocalAuthExternalMapper localAuthExternalMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResult result = localAuthLoginUseCase.execute(localAuthExternalMapper.localAuthLoginCommand(request));
        ResponseCookie cookie = createCookie(result);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        return ResponseEntity.ok(new LoginResponse(result.userId(), result.role()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        localAuthRegisterUseCase.execute(localAuthExternalMapper.localAuthRegisterCommand(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(CookieConstants.ACCESS_TOKEN_NAME) String accessToken,
                                       HttpServletResponse response) {
        if (Objects.nonNull(accessToken)) {
            localAuthLogoutUseCase.execute(new LocalAuthLogoutCommand(accessToken));
            deleteCookie(response);
        }
        return ResponseEntity.ok().build();
    }

    private ResponseCookie createCookie(LoginResult result) {
        return ResponseCookie.from(CookieConstants.ACCESS_TOKEN_NAME, result.token())
            .httpOnly(CookieConstants.HTTP_ONLY)
            .secure(false)
            .path(CookieConstants.DEFAULT_PATH)
            .maxAge(result.expiresIn())
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
