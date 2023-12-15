package com.kosa.mapbegood.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MemberInfoDTO implements Serializable{
    private String email;
    private String nickName;
    private String profileImage;
    private Long status;

    @QueryProjection
    public MemberInfoDTO(String email, String nickName, String profileImage, Long status) {
        this.email = email;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.status = status;
    }
}
