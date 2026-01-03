package com.lxp.auth.domain.local.service;

import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.domain.common.exception.LoginFailureException;
import com.lxp.auth.domain.local.model.entity.LocalAuth;
import com.lxp.auth.domain.local.model.vo.HashedPassword;
import com.lxp.auth.domain.local.policy.PasswordPolicy;
import com.lxp.auth.domain.local.service.spec.AuthCreateSpec;
import com.lxp.auth.domain.local.service.spec.UserLoginSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocalAuthService 테스트")
class LocalAuthServiceTest {

    @Mock
    private PasswordPolicy passwordPolicy;

    private LocalAuthService localAuthService;

    @BeforeEach
    void setUp() {
        localAuthService = new LocalAuthService(passwordPolicy);
    }

    @Test
    @DisplayName("새로운 LocalAuth를 성공적으로 생성한다")
    void createLocalAuth_Success() {
        // given: 유효한 이메일과 비밀번호가 주어지고, 비밀번호 정책이 해시된 비밀번호를 반환한다
        String email = "test@example.com";
        String password = "password123";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        AuthCreateSpec spec = new AuthCreateSpec(email, password);

        given(passwordPolicy.apply(password)).willReturn(hashedPassword);

        // when: LocalAuth 생성을 요청하면
        LocalAuth result = localAuthService.create(spec);

        // then: LocalAuth가 생성되고, 이메일과 해시된 비밀번호가 설정된다
        assertThat(result).isNotNull();
        assertThat(result.loginIdentifier()).isEqualTo(email);
        assertThat(result.hashedPassword()).isEqualTo(hashedPassword);
        verify(passwordPolicy).apply(password);
    }

    @Test
    @DisplayName("AuthCreateSpec이 null이면 예외가 발생한다")
    void createLocalAuth_NullSpec_ThrowsException() {
        // given: AuthCreateSpec이 null로 주어지면

        // when: LocalAuth 생성을 요청하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> localAuthService.create(null))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("사용자 필수 정보가 없습니다.");
    }

    @Test
    @DisplayName("유효한 비밀번호로 인증에 성공한다")
    void authenticate_ValidPassword_Success() {
        // given: 유효한 이메일, 비밀번호, 해시된 비밀번호가 주어지고, 비밀번호 매칭이 성공한다
        String email = "test@example.com";
        String password = "password123";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        UserLoginSpec spec = new UserLoginSpec(email, password, hashedPassword);

        given(passwordPolicy.isMatch(password, hashedPassword)).willReturn(true);

        // when: 인증을 요청하면
        // then: 예외가 발생하지 않는다
        assertThatCode(() -> localAuthService.authenticate(spec))
            .doesNotThrowAnyException();
        verify(passwordPolicy).isMatch(password, hashedPassword);
    }

    @Test
    @DisplayName("잘못된 비밀번호로 인증에 실패한다")
    void authenticate_InvalidPassword_ThrowsException() {
        // given: 유효한 이메일과 해시된 비밀번호가 주어지지만, 비밀번호 매칭이 실패한다
        String email = "test@example.com";
        String password = "wrongPassword";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        UserLoginSpec spec = new UserLoginSpec(email, password, hashedPassword);

        given(passwordPolicy.isMatch(password, hashedPassword)).willReturn(false);

        // when: 인증을 요청하면
        // then: LoginFailureException이 발생한다
        assertThatThrownBy(() -> localAuthService.authenticate(spec))
            .isInstanceOf(LoginFailureException.class);
        verify(passwordPolicy).isMatch(password, hashedPassword);
    }

    @Test
    @DisplayName("UserLoginSpec이 null이면 인증 시 예외가 발생한다")
    void authenticate_NullSpec_ThrowsException() {
        // given: UserLoginSpec이 null로 주어지면

        // when: 인증을 요청하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> localAuthService.authenticate(null))
            .isInstanceOf(AuthException.class);
    }
}
