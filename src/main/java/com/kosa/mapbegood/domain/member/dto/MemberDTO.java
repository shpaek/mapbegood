package com.kosa.mapbegood.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDto {
        @NotBlank(message = "Email은 공백이 아니어야 합니다.")
        @Email(message = "올바른 이메일 형태가 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()?])[A-Za-z\\d!@#$%^&*()?]{8,}$",
                message = "비밀번호는 8자리 이상의 영문 대소문자, 숫자, 특수문자 조합이어야 합니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
        private String nickname;

        @NotBlank(message = "Email은 공백이 아니어야 합니다.")
        @Email(message = "올바른 이메일 형태가 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()?])[A-Za-z\\d!@#$%^&*()?]{8,}$",
                message = "비밀번호는 8자리 이상의 영문 대소문자, 숫자, 특수문자 조합이어야 합니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Nickname {
        @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
        private String nickname;
    }
}
