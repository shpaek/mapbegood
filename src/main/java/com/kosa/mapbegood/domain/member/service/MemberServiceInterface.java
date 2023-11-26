package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberServiceInterface {
    public void signup(Member member);
    public void updateNickName();
    public void updatePassword();
    public void findPassword();
    public void searchUser();
    public void deleteUser();
}
