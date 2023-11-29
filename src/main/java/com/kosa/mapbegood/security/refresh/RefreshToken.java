package com.kosa.mapbegood.security.refresh;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash(value = "refreshToken", timeToLive = 25200)
public class RefreshToken {
    @Id
    private String refreshToken;
    private String logOut;

    public RefreshToken(String refreshToken, String logOut) {
        this.refreshToken = refreshToken;
        this.logOut = logOut;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String isLogOut() {
        return this.logOut;
    }
}
