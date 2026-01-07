package com.lxp.auth.domain.local.model.entity;

import com.lxp.auth.domain.common.exception.AuthException;
import com.lxp.auth.domain.common.model.vo.UserId;
import com.lxp.auth.domain.local.model.vo.HashedPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("LocalAuth 엔티티 테스트")
class LocalAuthTest {

    @Test
    @DisplayName("로그인 식별자와 해시된 비밀번호로 LocalAuth를 등록한다")
    void register_WithValidParameters_Success() {
        // given: 유효한 로그인 식별자와 해시된 비밀번호가 주어지면
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");

        // when: LocalAuth를 등록하면
        LocalAuth localAuth = LocalAuth.register(loginIdentifier, hashedPassword);

        // then: LocalAuth가 생성되고, userId, 로그인 식별자, 해시된 비밀번호가 설정된다
        assertThat(localAuth).isNotNull();
        assertThat(localAuth.userId()).isNotNull();
        assertThat(localAuth.loginIdentifier()).isEqualTo(loginIdentifier);
        assertThat(localAuth.hashedPassword()).isEqualTo(hashedPassword);
    }

    @Test
    @DisplayName("userId, 로그인 식별자, 해시된 비밀번호로 LocalAuth를 생성한다")
    void of_WithValidParameters_Success() {
        // given: 유효한 userId, 로그인 식별자, 해시된 비밀번호가 주어지면
        UserId userId = UserId.create();
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");

        // when: LocalAuth를 생성하면
        LocalAuth localAuth = LocalAuth.of(userId, loginIdentifier, hashedPassword);

        // then: LocalAuth가 생성되고, 모든 필드가 설정된다
        assertThat(localAuth).isNotNull();
        assertThat(localAuth.userId()).isEqualTo(userId);
        assertThat(localAuth.loginIdentifier()).isEqualTo(loginIdentifier);
        assertThat(localAuth.hashedPassword()).isEqualTo(hashedPassword);
    }

    @Test
    @DisplayName("userId가 null이면 예외가 발생한다")
    void of_NullUserId_ThrowsException() {
        // given: userId가 null로 주어지면
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");

        // when: LocalAuth를 생성하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> LocalAuth.of(null, loginIdentifier, hashedPassword))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("userId는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("로그인 식별자가 null이면 예외가 발생한다")
    void of_NullLoginIdentifier_ThrowsException() {
        // given: 로그인 식별자가 null로 주어지면
        UserId userId = UserId.create();
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");

        // when: LocalAuth를 생성하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> LocalAuth.of(userId, null, hashedPassword))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("loginIdentifier는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("해시된 비밀번호가 null이면 예외가 발생한다")
    void of_NullHashedPassword_ThrowsException() {
        // given: 해시된 비밀번호가 null로 주어지면
        UserId userId = UserId.create();
        String loginIdentifier = "test@example.com";

        // when: LocalAuth를 생성하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> LocalAuth.of(userId, loginIdentifier, null))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("hashedPassword는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호를 성공적으로 업데이트한다")
    void updatePassword_WithValidPassword_Success() {
        // given: LocalAuth가 생성되고, 새로운 해시된 비밀번호가 주어지면
        String loginIdentifier = "test@example.com";
        HashedPassword originalPassword = new HashedPassword("originalPassword123");
        LocalAuth localAuth = LocalAuth.register(loginIdentifier, originalPassword);

        HashedPassword newPassword = new HashedPassword("newPassword456");

        // when: 비밀번호를 업데이트하면
        localAuth.updatePassword(newPassword);

        // then: 비밀번호가 업데이트된다
        assertThat(localAuth.hashedPassword()).isEqualTo(newPassword);
        assertThat(localAuth.hashedPassword()).isNotEqualTo(originalPassword);
    }

    @Test
    @DisplayName("null 비밀번호로 업데이트하면 예외가 발생한다")
    void updatePassword_WithNullPassword_ThrowsException() {
        // given: LocalAuth가 생성되고, null 비밀번호가 주어지면
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        LocalAuth localAuth = LocalAuth.register(loginIdentifier, hashedPassword);

        // when: null 비밀번호로 업데이트하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> localAuth.updatePassword(null))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("비밀번호는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("UserId 객체로 ID 일치 여부를 확인한다")
    void matchesId_WithUserId_ReturnsTrue() {
        // given: LocalAuth가 생성되고, 동일한 userId가 주어지면
        UserId userId = UserId.create();
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        LocalAuth localAuth = LocalAuth.of(userId, loginIdentifier, hashedPassword);

        // when: userId로 일치 여부를 확인하면
        boolean result = localAuth.matchesId(userId);

        // then: true를 반환한다
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("다른 UserId 객체로 ID 일치 여부를 확인하면 false를 반환한다")
    void matchesId_WithDifferentUserId_ReturnsFalse() {
        // given: LocalAuth가 생성되고, 다른 userId가 주어지면
        UserId userId = UserId.create();
        UserId differentUserId = UserId.create();
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        LocalAuth localAuth = LocalAuth.of(userId, loginIdentifier, hashedPassword);

        // when: 다른 userId로 일치 여부를 확인하면
        boolean result = localAuth.matchesId(differentUserId);

        // then: false를 반환한다
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("문자열로 ID 일치 여부를 확인한다")
    void matchesId_WithString_ReturnsTrue() {
        // given: LocalAuth가 생성되고, userId의 문자열 표현이 주어지면
        UserId userId = UserId.create();
        String loginIdentifier = "test@example.com";
        HashedPassword hashedPassword = new HashedPassword("hashedPassword123");
        LocalAuth localAuth = LocalAuth.of(userId, loginIdentifier, hashedPassword);

        // when: 문자열로 일치 여부를 확인하면
        boolean result = localAuth.matchesId(userId.asString());

        // then: true를 반환한다
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해시된 비밀번호를 문자열로 반환한다")
    void hashedPasswordAsString_ReturnsCorrectValue() {
        // given: LocalAuth가 생성되면
        String loginIdentifier = "test@example.com";
        String passwordValue = "hashedPassword123";
        HashedPassword hashedPassword = new HashedPassword(passwordValue);
        LocalAuth localAuth = LocalAuth.register(loginIdentifier, hashedPassword);

        // when: 해시된 비밀번호를 문자열로 요청하면
        String result = localAuth.hashedPasswordAsString();

        // then: 비밀번호의 값을 문자열로 반환한다
        assertThat(result).isEqualTo(passwordValue);
    }
}
