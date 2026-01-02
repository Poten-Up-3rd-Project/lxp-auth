package com.lxp.auth.domain.local.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HashedPassword VO 테스트")
class HashedPasswordTest {

    @Test
    @DisplayName("유효한 값으로 HashedPassword를 생성한다")
    void create_WithValidValue_Success() {
        // given: 유효한 해시된 비밀번호 값이 주어지면
        String passwordValue = "hashedPassword123";

        // when: HashedPassword를 생성하면
        HashedPassword hashedPassword = new HashedPassword(passwordValue);

        // then: HashedPassword가 생성되고 값이 설정된다
        assertThat(hashedPassword).isNotNull();
        assertThat(hashedPassword.value()).isEqualTo(passwordValue);
    }

    @Test
    @DisplayName("null 값으로 HashedPassword 생성 시 예외가 발생한다")
    void create_WithNullValue_ThrowsException() {
        // given: null 값이 주어지면

        // when: HashedPassword를 생성하면
        // then: IllegalArgumentException이 발생한다
        assertThatThrownBy(() -> new HashedPassword(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("HashedPassword value cannot be empty.");
    }

    @Test
    @DisplayName("빈 문자열로 HashedPassword 생성 시 예외가 발생한다")
    void create_WithEmptyValue_ThrowsException() {
        // given: 빈 문자열이 주어지면
        String emptyValue = "";

        // when: HashedPassword를 생성하면
        // then: IllegalArgumentException이 발생한다
        assertThatThrownBy(() -> new HashedPassword(emptyValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("HashedPassword value cannot be empty.");
    }

    @Test
    @DisplayName("공백 문자열로 HashedPassword 생성 시 예외가 발생한다")
    void create_WithBlankValue_ThrowsException() {
        // given: 공백 문자열이 주어지면
        String blankValue = "   ";

        // when: HashedPassword를 생성하면
        // then: IllegalArgumentException이 발생한다
        assertThatThrownBy(() -> new HashedPassword(blankValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("HashedPassword value cannot be empty.");
    }

    @Test
    @DisplayName("동일한 값으로 생성된 HashedPassword는 같다")
    void equals_WithSameValue_ReturnsTrue() {
        // given: 동일한 값으로 두 개의 HashedPassword가 생성되면
        String passwordValue = "hashedPassword123";
        HashedPassword password1 = new HashedPassword(passwordValue);
        HashedPassword password2 = new HashedPassword(passwordValue);

        // when: 두 객체를 비교하면
        // then: 동일하다고 판단한다
        assertThat(password1).isEqualTo(password2);
        assertThat(password1.hashCode()).isEqualTo(password2.hashCode());
    }

    @Test
    @DisplayName("다른 값으로 생성된 HashedPassword는 다르다")
    void equals_WithDifferentValue_ReturnsFalse() {
        // given: 다른 값으로 두 개의 HashedPassword가 생성되면
        HashedPassword password1 = new HashedPassword("hashedPassword123");
        HashedPassword password2 = new HashedPassword("hashedPassword456");

        // when: 두 객체를 비교하면
        // then: 다르다고 판단한다
        assertThat(password1).isNotEqualTo(password2);
    }
}
