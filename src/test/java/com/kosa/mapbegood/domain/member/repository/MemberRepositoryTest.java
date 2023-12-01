package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository repository;
	

	@Autowired
	private PasswordEncoder pwEncoder;

	@Test
	@DisplayName("회원 조회")
	public void selectMember() {
		// given + when
		String email = "test@mail.com";
		String nick = "test";
		String pwd = "test";

		Optional<Member> optMember = repository.findById(email);
		Member m = null;

		if (optMember.isPresent()) {
			m = optMember.get();
		}

		// then
		assertNotNull(optMember);
		assertEquals(nick, m.getNickname());
		assertTrue(pwEncoder.matches(pwd, m.getPassword()));
//		log.error(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		log.error(m.getCreatedAt().toString());
	}

	@Test
	@DisplayName("회원 가입")
	public void insertMember() {
		// given
		String nick = "test1";
		String email = "test1@mail.com";
		String pwd = "test1";
		int status = 1;

		Member m = Member.builder()
				.email(email)
				.nickname(nick)
				.password(pwEncoder.encode(pwd))
				.status(status)
				.build();

		// when
		Member savedMember = repository.save(m);

		// then
		assertNotNull(savedMember);
		assertEquals(email, savedMember.getEmail());
		assertEquals(nick, savedMember.getNickname());
		assertTrue(pwEncoder.matches(pwd, savedMember.getPassword()));
	}

	@Test
	@DisplayName("회원 정보 수정")
	public void updateMember() {
		// given
		String email = "test@mail.com";
		String updateNick = "updateTest";
		String updatePwd = "updatePwd";

		Optional<Member> optMember = repository.findById(email);
		Member m = null;

		if (optMember.isPresent()) {
			m = optMember.get();
			m.setNickname(updateNick);
			m.setPassword(pwEncoder.encode(updatePwd));
		}

		// when
		Member updateMember = repository.save(m);

		// then
		assertEquals(updateNick, updateMember.getNickname());
		assertTrue(pwEncoder.matches(updatePwd, updateMember.getPassword()));
	}

	@Test
	@DisplayName("회원 삭제")
	public void deleteMember() {
		// given + when
		String email = "test@mail.com";

		repository.deleteById(email);

		// then
	}
	

}
