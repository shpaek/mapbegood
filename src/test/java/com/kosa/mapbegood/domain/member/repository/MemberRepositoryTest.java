package com.kosa.mapbegood.domain.member.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository repository;
	

//	@Autowired
//	private PasswordEncoder pwEncoder;

	@Test
	@DisplayName("회원 조회")
	public void selectMember() {
		// given + when
		String nick = "test";
		String email = "test@mail.com";
		String pwd = "test";

		Optional<Member> optMember = repository.findById(nick);
		Member m = null;

		if (optMember.isPresent()) {
			m = optMember.get();
		}

		// then
		assertNotNull(optMember);
		assertEquals(email, m.getEmail());
		assertEquals(pwd, m.getPassword());
		log.error(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
	}

	@Test
	@DisplayName("회원 가입")
	public void insertMember() {
		// given
		String nick = "test";
		String email = "test@mail.com";
		String pwd = "test";

		Member m = Member.builder()
				.nickname(nick)
				.email(email)
				.password(pwd)
//				.password(pwEncoder.encode("test"))
				.build();

		// when
		Member savedMember = repository.save(m);

		// then
		assertNotNull(savedMember);
		assertEquals(nick, savedMember.getNickname());
		assertEquals(email, savedMember.getEmail());
		assertEquals(pwd, savedMember.getPassword());
	}

	@Test
	@DisplayName("회원 정보 수정")
	public void updateMember() {
		// given
		String nick = "test";
		String updateEmail = "updateEmail";
		String updatePwd = "updatePwd";

		Optional<Member> optMember = repository.findById(nick);
		Member m = null;

		if (optMember.isPresent()) {
			m = optMember.get();
			m.setEmail(updateEmail);
			m.setPassword(updatePwd);
		}

		// when
		Member updateMember = repository.save(m);

		// then
		assertEquals(updateEmail, updateMember.getEmail());
		assertEquals(updatePwd, updateMember.getPassword());
	}

	@Test
	@DisplayName("회원 삭제")
	public void deleteMember() {
		// given + when
		String nick = "test";

		repository.deleteById(nick);

		// then
	}
	

}
