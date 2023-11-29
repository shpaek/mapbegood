package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberNickNameDTO {
    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;
}
