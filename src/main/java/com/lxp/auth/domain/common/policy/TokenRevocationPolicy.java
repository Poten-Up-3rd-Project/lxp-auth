package com.lxp.auth.domain.common.policy;

public interface TokenRevocationPolicy {

    /**
     * 유효한 Access Token을 블랙리스트에 추가합니다.
     *
     * @param token           Access Token 문자열
     * @param durationSeconds 토큰의 남은 유효 시간 (TTL)
     */
    void revokeAccessToken(String token, long durationSeconds);

}
