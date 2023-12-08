package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDTO implements Serializable{
    private String email;
    private String nickName;
    private String profileImage;
}
