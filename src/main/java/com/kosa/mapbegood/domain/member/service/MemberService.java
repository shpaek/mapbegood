package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;


@Slf4j
@Service
public class MemberService implements MemberServiceInterface {
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private MemberRepository repository;

	@Override
	public void createMember(Member member) {
		log.error("MemberService: signup()");
		member.setPassword(pwEncoder.encode(member.getPassword()));
		System.out.println(repository.save(member));
	}

	public void logout() {
	}

	@Override
	public void findNickName(String nickName) throws AddException {
		Optional<Member> findMember = repository.findById(nickName);
		if (findMember.isPresent()) {
			throw new AddException("해당 닉네임은 이미 사용중입니다.");
		}
	}

	@Override
	public void updateNickName(String email, String nickName) throws FindException {
		log.error("MemberService: updateNickName()");
		Optional<Member> opMember = repository.findByEmail(email);
		Member findMember = opMember.orElseThrow(() -> new FindException(email + " 회원을 찾을 수 없습니다."));
		findMember.setNickname(nickName);
		repository.save(findMember);
	}

	@Override
	public void updatePassword() {

	}

	@Override
	public void findPassword() {

	}

	@Override
	public void searchUser() {

	}

	@Override
	public void deleteUser() {

	}
}
