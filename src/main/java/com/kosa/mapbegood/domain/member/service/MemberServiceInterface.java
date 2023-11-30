package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

import java.util.List;

public interface MemberServiceInterface {

    /**
     * 사용자 조회
     * @param email
     * @throws FindException
     */
    Member findMember(String email) throws Exception;

    /**
     * 회원 가입
     * @param member
     * @throws AddException
     */
    void createMember(Member member) throws Exception;

    /**
     * 닉네임 중복 확인
     * @param nickName
     * @throws AddException
     */
    void duplicationNickName(String nickName) throws Exception;

    /**
     * 패스워드 재인증
     * @param email
     * @param password
     * @throws Exception
     */
    void verifyPassword(String email, String password) throws Exception;

    /**
     * 닉네임 수정
     * @param email
     * @param nickName
     * @throws ModifyException
     */
    void updateNickName(String email, String nickName) throws Exception;

    /**
     * 패스워드 수정
     * @param email
     * @param password
     * @throws ModifyException
     */
    void updatePassword(String email, String password) throws Exception;

    /**
     * 비밀번호 찾기(이메일 전송)
     * @param email
     * @throws Exception
     */
    void sendCodeToEmail(String email) throws Exception;

    /**
     * 비밀번호 찾기(문자일 인증)
     * @param email
     * @param code
     * @return
     * @throws Exception
     */
    boolean verifiedCode(String email, String code) throws Exception;;

    // TODO: 2023-11-30
    /**
     * 사용자 nickname 검색
     */
    List<String> searchMember(String email, String nickName) throws Exception;

    /**
     * 회원 탈퇴
     * @param email
     * @throws RemoveException
     */
    void deleteMember(String email) throws Exception;
}
