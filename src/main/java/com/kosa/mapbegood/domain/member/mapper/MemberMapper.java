package com.kosa.mapbegood.domain.member.mapper;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.dto.MemberSignUpDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    default Member MemberDTOPostToMember(MemberSignUpDTO memberSignUpDto) {
        return Member.builder()
                .email(memberSignUpDto.getEmail())
                .nickname(memberSignUpDto.getNickname())
                .password(memberSignUpDto.getPassword())
                .build();
    }

    Member memberPostToMember(MemberDTO memberDTO);
}
