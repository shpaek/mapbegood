package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberNickNameDTO {
    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;
}
