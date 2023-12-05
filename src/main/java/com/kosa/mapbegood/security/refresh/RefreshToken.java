package com.kosa.mapbegood.security.refresh;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@RedisHash(timeToLive = 21600)
public class RefreshToken {
    @Id
    private String rtk;
    private String logout;

    public String getRefreshToken() {
        return this.rtk;
    }

    public String getLogout() {
        return this.logout;
    }
}
