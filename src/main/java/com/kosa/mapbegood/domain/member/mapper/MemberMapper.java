package com.kosa.mapbegood.domain.member.mapper;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member MemberDTOPostToMember(MemberDTO.Post memberDTOPost) {
        return Member.builder()
                .email(memberDTOPost.getEmail())
                .nickname(memberDTOPost.getNickname())
                .password(memberDTOPost.getPassword())
                .build();
    }
}
