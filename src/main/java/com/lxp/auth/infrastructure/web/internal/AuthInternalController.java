package com.lxp.auth.infrastructure.web.internal;

import com.lxp.auth.application.port.provided.command.TokenRevokeCommand;
import com.lxp.auth.application.port.provided.dto.AuthTokenResult;
import com.lxp.auth.application.port.provided.usecase.TokenRegeneratedUseCase;
import com.lxp.auth.application.port.provided.usecase.TokenRevokeUseCase;
import com.lxp.auth.infrastructure.web.internal.dto.InternalRegenerateTokenRequest;
import com.lxp.auth.infrastructure.web.internal.dto.InternalRevokeTokenRequest;
import com.lxp.auth.infrastructure.web.internal.dto.InternalTokenResponse;
import com.lxp.auth.infrastructure.web.internal.mapper.AuthInternalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api-v1/auth")
@RequiredArgsConstructor
public class AuthInternalController {

    private final TokenRegeneratedUseCase tokenRegeneratedUseCase;
    private final TokenRevokeUseCase tokenRevokeUseCase;
    private final AuthInternalMapper authInternalMapper;

    @PostMapping("/regenerate")
    ResponseEntity<InternalTokenResponse> regenerateToken(@AuthenticationPrincipal String userId,
                                                          @RequestBody InternalRegenerateTokenRequest request) {
        AuthTokenResult result = tokenRegeneratedUseCase.execute(
            authInternalMapper.toTokenRegeneratedCommand(request, userId)
        );
        return ResponseEntity.ok(authInternalMapper.toInternalTokenResponse(result));
    }

    @PostMapping("/revoke")
    ResponseEntity<Void> revokeToken(@AuthenticationPrincipal String userId,
                                     @RequestBody InternalRevokeTokenRequest request) {
        tokenRevokeUseCase.execute(new TokenRevokeCommand(userId, request.token()));
        return ResponseEntity.ok().build();
    }

}
