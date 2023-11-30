package com.kosa.mapbegood.security.refresh;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(timeToLive = 21600)
public class RefreshToken implements Serializable {
    @Id
    private String rtk;
    private String logout;

    public RefreshToken(String rtk, String logout) {
        this.rtk = rtk;
        this.logout = logout;
    }

    public String getRefreshToken() {
        return this.rtk;
    }

    public String getLogout() {
        return this.logout;
    }
}
