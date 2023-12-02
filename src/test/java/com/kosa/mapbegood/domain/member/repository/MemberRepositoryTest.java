package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberRepositoryTest {
	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder pwEncoder;
	@PersistenceContext
	EntityManager em;

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

	@Test
	void queryDSLTest() {
		// given
		String email = "qTest@mail.com";
		String nickName = "qTest";
		String password = "qwe123!@#";

		Member member = new Member().builder()
				.email(email)
				.nickname(nickName)
				.password(pwEncoder.encode(password))
				.build();

		em.persist(member);
		em.flush();
		em.clear();

		QMember qm = new QMember("test");
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		// when
		Member findMember = queryFactory
				.selectFrom(qm)
				.where(qm.nickname.eq(nickName))
				.fetchOne();

		// then
		assertNotNull(findMember);
		assertThat(findMember.getEmail()).isEqualTo(email);
	}
}
