package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;

public interface MemberServiceInterface {
    /**
     * 회원 가입
     * @param member
     */
    void createMember(Member member);

    /**
     * 닉네임 중복 확인
     * @param nickName
     * @throws AddException
     */
    void findNickName(String nickName) throws AddException;

    /**
     * 닉네임 수정
     * @param email
     * @param nickName
     * @throws FindException
     */
    void updateNickName(String email, String nickName) throws FindException;

    /**
     * 패스워드 수정
     */
    void updatePassword();

    /**
     * 패스워드 찾기
     */
    void findPassword();

    /**
     * 사용자 nickname 검색
     */
    void searchUser();

    /**
     * 탈퇴
     */
    void deleteUser();
}
