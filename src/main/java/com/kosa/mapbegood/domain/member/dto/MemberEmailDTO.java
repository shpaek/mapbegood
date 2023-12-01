package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailDTO {
    @NotBlank(message = "Email은 공백이 아니어야 합니다.")
    @Email(message = "올바른 이메일 형태가 아닙니다.")
    private String email;
}
