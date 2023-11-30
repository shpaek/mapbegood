package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService implements MemberServiceInterface {
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private MemberRepository repository;

	@Override
	public Member findMember(String email) throws FindException {
		try {
			Optional<Member> optMember = repository.findById(email);
			return optMember.orElseThrow(() -> new FindException());
		} catch (FindException fe) {
			throw new FindException("[ " + email + " ] 해당 이메일로 가입된 회원이 없습니다.");
		} catch (Exception e) {
			log.error("회원 조회 실패: " + e.getMessage());
			throw new FindException();
		}
	}

	@Override
	public void createMember(Member member) throws AddException {
		try {
			member.setPassword(pwEncoder.encode(member.getPassword()));
			repository.save(member);
		} catch (Exception e) {
			log.error("회원가입 실패: " + e.getMessage());
			throw new AddException();
		}
	}

	// TODO: 2023-11-29  
	public void logout() {
	}

	@Override
	public void duplicationNickName(String nickName) throws AddException {
		try {
			Optional<Member> findMember = repository.findByNickname(nickName);
			if (findMember.isPresent()) {
				throw new AddException();
			}
		} catch (AddException ae) {
			throw new AddException();
		} catch (Exception e) {
			log.error("닉네임 중복 확인 실패: " + e.getMessage());
			throw new AddException();
		}
	}

	@Override
	public void updateNickName(String email, String nickName) throws ModifyException {
		try {
			Member member = findMember(email);
			member.setNickname(nickName);
			repository.save(member);
		} catch (FindException fe) {
			log.error(fe.getMessage());
			throw new ModifyException();
		} catch (Exception e) {
			log.error("닉네임 수정 실패: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Override
	public void updatePassword(String email, String password) throws ModifyException {
		try {
			Member member = findMember(email);
			member.setPassword(pwEncoder.encode(password));
			repository.save(member);
		} catch (FindException fe) {
			log.error(fe.getMessage());
			throw new ModifyException();
		} catch (Exception e) {
			log.error("패스워드 수정 실패: " + e.getMessage());
			throw new ModifyException();
		}
	}

	// TODO: 2023-11-29  
	@Override
	public void findPassword() {

	}

	// TODO: 2023-11-29  
	@Override
	public List<String> searchMember(String email, String nickName) throws FindException {
		repository.findAllByNickname(nickName);
		return null;
	}

	@Override
	public void deleteMember(String email) throws RemoveException {
		try {
			Member member = findMember(email);
			member.setStatus(0);
			repository.save(member);
		} catch (FindException fe) {
			log.error(fe.getMessage());
			throw new RemoveException();
		} catch (Exception e) {
			log.error("회원탈퇴 실패: " + e.getMessage());
			throw new RemoveException();
		}
	}
}
