package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
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

import java.util.Optional;

@Slf4j
@Service
public class MemberService implements MemberServiceInterface {
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private MemberRepository repository;

	@Override
	public void signup(Member member) {
		log.error("MemberService: signup()");
		member.setPassword(pwEncoder.encode(member.getPassword()));
		System.out.println(repository.save(member));
	}

	@Override
	public void login() {

	}

	@Override
	public void logout() {

	}

	@Override
	public void updateNickName() {

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

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = repository.findByEmail(username);
		Member findMember = optionalMember.orElseThrow(() -> new FindException("회원을 찾을 수 없음"));
//        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		return new User(findMember.getEmail(), findMember.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
	}
}
