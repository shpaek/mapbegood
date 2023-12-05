package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPassDTO {
    @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()?])[A-Za-z\\d!@#$%^&*()?]{8,}$",
            message = "비밀번호는 8자리 이상의 영문 대소문자, 숫자, 특수문자 조합이어야 합니다.")
    private String password;
}
