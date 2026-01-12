package com.lxp.auth.domain.common.model.vo;

import com.lxp.auth.domain.common.exception.AuthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserId VO 테스트")
class UserIdTest {

    @Test
    @DisplayName("랜덤 UUID로 UserId를 생성한다")
    void create_GeneratesRandomUserId() {
        // given: UserId 생성 요청이 주어지면

        // when: UserId를 생성하면
        UserId userId = UserId.create();

        // then: null이 아닌 UserId가 생성된다
        assertThat(userId).isNotNull();
        assertThat(userId.value()).isNotNull();
    }

    @Test
    @DisplayName("생성된 UserId는 매번 다르다")
    void create_GeneratesDifferentIds() {
        // given: 두 번의 UserId 생성 요청이 주어지면

        // when: 두 개의 UserId를 생성하면
        UserId userId1 = UserId.create();
        UserId userId2 = UserId.create();

        // then: 두 UserId는 서로 다르다
        assertThat(userId1).isNotEqualTo(userId2);
        assertThat(userId1.value()).isNotEqualTo(userId2.value());
    }

    @Test
    @DisplayName("UUID 객체로 UserId를 생성한다")
    void of_WithUUID_Success() {
        // given: 유효한 UUID 객체가 주어지면
        UUID uuid = UUID.randomUUID();

        // when: UserId를 생성하면
        UserId userId = UserId.of(uuid);

        // then: 주어진 UUID를 가진 UserId가 생성된다
        assertThat(userId).isNotNull();
        assertThat(userId.value()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("null UUID로 UserId 생성 시 예외가 발생한다")
    void of_WithNullUUID_ThrowsException() {
        // given: null UUID가 주어지면
        UUID nullUuid = null;

        // when: UserId를 생성하면
        // then: AuthException이 발생한다
        assertThatThrownBy(() -> UserId.of(nullUuid))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("userId의 value는 null일 수 없습니다.");
    }

    @Test
    @DisplayName("문자열로 UserId를 생성한다")
    void of_WithString_Success() {
        // given: 유효한 UUID 문자열이 주어지면
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // when: 문자열로 UserId를 생성하면
        UserId userId = UserId.of(uuidString);

        // then: 해당 UUID를 가진 UserId가 생성된다
        assertThat(userId).isNotNull();
        assertThat(userId.value()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("잘못된 형식의 문자열로 UserId 생성 시 예외가 발생한다")
    void of_WithInvalidString_ThrowsException() {
        // given: 잘못된 형식의 UUID 문자열이 주어지면
        String invalidUuidString = "invalid-uuid-string";

        // when: UserId를 생성하면
        // then: IllegalArgumentException이 발생한다
        assertThatThrownBy(() -> UserId.of(invalidUuidString))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("UUID 객체로 ID 일치 여부를 확인한다")
    void matches_WithUUID_ReturnsTrue() {
        // given: UserId가 생성되고 동일한 UUID가 주어지면
        UUID uuid = UUID.randomUUID();
        UserId userId = UserId.of(uuid);

        // when: UUID로 일치 여부를 확인하면
        boolean result = userId.matches(uuid);

        // then: true를 반환한다
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("다른 UUID 객체로 ID 일치 여부를 확인하면 false를 반환한다")
    void matches_WithDifferentUUID_ReturnsFalse() {
        // given: UserId가 생성되고 다른 UUID가 주어지면
        UserId userId = UserId.of(UUID.randomUUID());
        UUID differentUuid = UUID.randomUUID();

        // when: 다른 UUID로 일치 여부를 확인하면
        boolean result = userId.matches(differentUuid);

        // then: false를 반환한다
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("UserId 객체로 ID 일치 여부를 확인한다")
    void matches_WithUserId_ReturnsTrue() {
        // given: 동일한 UUID를 가진 두 UserId가 생성되면
        UUID uuid = UUID.randomUUID();
        UserId userId1 = UserId.of(uuid);
        UserId userId2 = UserId.of(uuid);

        // when: UserId로 일치 여부를 확인하면
        boolean result = userId1.matches(userId2);

        // then: true를 반환한다
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("문자열로 ID 일치 여부를 확인한다")
    void matches_WithString_ReturnsTrue() {
        // given: UserId가 생성되고 동일한 UUID의 문자열 표현이 주어지면
        UUID uuid = UUID.randomUUID();
        UserId userId = UserId.of(uuid);
        String uuidString = uuid.toString();

        // when: 문자열로 일치 여부를 확인하면
        boolean result = userId.matches(uuidString);

        // then: true를 반환한다
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("다른 문자열로 ID 일치 여부를 확인하면 false를 반환한다")
    void matches_WithDifferentString_ReturnsFalse() {
        // given: UserId가 생성되고 다른 UUID의 문자열이 주어지면
        UserId userId = UserId.of(UUID.randomUUID());
        String differentUuidString = UUID.randomUUID().toString();

        // when: 다른 문자열로 일치 여부를 확인하면
        boolean result = userId.matches(differentUuidString);

        // then: false를 반환한다
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("UserId를 문자열로 변환한다")
    void asString_ReturnsCorrectValue() {
        // given: UUID로 UserId가 생성되면
        UUID uuid = UUID.randomUUID();
        UserId userId = UserId.of(uuid);

        // when: 문자열로 변환하면
        String result = userId.asString();

        // then: UUID의 문자열 표현을 반환한다
        assertThat(result).isEqualTo(uuid.toString());
    }

    @Test
    @DisplayName("동일한 UUID를 가진 UserId는 같다")
    void equals_WithSameUUID_ReturnsTrue() {
        // given: 동일한 UUID를 가진 두 UserId가 생성되면
        UUID uuid = UUID.randomUUID();
        UserId userId1 = UserId.of(uuid);
        UserId userId2 = UserId.of(uuid);

        // when: 두 객체를 비교하면
        // then: 동일하다고 판단한다
        assertThat(userId1).isEqualTo(userId2);
        assertThat(userId1.hashCode()).isEqualTo(userId2.hashCode());
    }

    @Test
    @DisplayName("다른 UUID를 가진 UserId는 다르다")
    void equals_WithDifferentUUID_ReturnsFalse() {
        // given: 다른 UUID를 가진 두 UserId가 생성되면
        UserId userId1 = UserId.of(UUID.randomUUID());
        UserId userId2 = UserId.of(UUID.randomUUID());

        // when: 두 객체를 비교하면
        // then: 다르다고 판단한다
        assertThat(userId1).isNotEqualTo(userId2);
    }
}
