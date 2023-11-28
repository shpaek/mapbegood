package com.kosa.mapbegood.domain.member.mapper;

import com.kosa.mapbegood.domain.member.dto.MemberSignUpDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member MemberDTOPostToMember(MemberSignUpDTO memberSignUpDto) {
        return Member.builder()
                .email(memberSignUpDto.getEmail())
                .nickname(memberSignUpDto.getNickname())
                .password(memberSignUpDto.getPassword())
                .build();
    }
}
