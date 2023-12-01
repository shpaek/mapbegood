package com.kosa.mapbegood.domain.member.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.domain.common.service.MailService;
import com.kosa.mapbegood.domain.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements MemberServiceInterface {
	private static final String AUTH_CODE_PREFIX = "AuthCode ";
	private final PasswordEncoder pwEncoder;
	private final MemberRepository repository;
	private final MailService mailService;
	private final RedisService redisService;
	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	@Override
	public Member findMember(String email) throws Exception {
		try {
			Optional<Member> optMember = repository.findById(email);
			Member findMember = optMember.orElseThrow(FindException::new);
			if (findMember.getStatus() == 0) {
				throw new Exception("탈퇴한 계정입니다.");
			}
			return findMember;
		} catch (FindException fe) {
			log.error("[ " + email + " ] 해당 이메일로 가입된 회원이 없습니다.: " + fe.getMessage());
			throw fe;
		} catch (Exception e) {
			log.error("회원 조회 Error: " + e.getMessage());
			throw new FindException();
		}
	}

	@Override
	public void createMember(Member member) throws Exception {
		try {
			Optional<Member> optMember = repository.findById(member.getEmail());
			if (optMember.isEmpty()) {
				member.setPassword(pwEncoder.encode(member.getPassword()));
				member.setStatus(1);
				repository.save(member);
			} else {
				throw new AddException("이미 가입된 계정이 있습니다.");
			}
		} catch (AddException ae) {
			log.error(ae.getMessage());
			throw new AddException();
		} catch (Exception e) {
			log.error("회원가입 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public void duplicationNickName(String nickName) throws Exception {
		try {
			Optional<Member> findMember = repository.findByNickname(nickName);
			if (findMember.isPresent()) {
				throw new AddException("해당 닉네임은 이미 사용중입니다.");
			}
		} catch (AddException ae) {
			log.error(ae.getMessage());
			throw new AddException();
		} catch (Exception e) {
			log.error("닉네임 중복 확인 Error: " + e.getMessage());
			throw new AddException();
		}
	}

	@Override
	public void verifyPassword(String email, String password) throws Exception {
		try {
			Member member = findMember(email);
			if (!member.getPassword().equals(pwEncoder.encode(password))) {
				throw new Exception();
			};
		} catch (Exception e) {
			log.error("패스워드 검증 실패: " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public void updateNickName(String email, String nickName) throws Exception {
		try {
			Member member = findMember(email);
			member.setNickname(nickName);
			repository.save(member);
		} catch (Exception e) {
			log.error("닉네임 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Override
	public void updatePassword(String email, String password) throws Exception {
		try {
			Member member = findMember(email);
			member.setPassword(pwEncoder.encode(password));
			repository.save(member);
		} catch (Exception e) {
			log.error("패스워드 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Override
	public void sendCodeToEmail(String email) throws Exception {
		findMember(email);
		String title = "[MapBeGood] Email Verification String";
		String authCode = RandomStringUtils.randomAlphanumeric(10);
		try {
			mailService.sendEmail(email, title, authCode);
		} catch (Exception e) {
			log.error("이메일 전송 Error: " + e.getMessage());
			throw new Exception();
		}
		try {
			redisService.setValues(
					AUTH_CODE_PREFIX + email,
					authCode,
					Duration.ofMillis(this.authCodeExpirationMillis)
			);
		} catch (Exception e) {
			log.error("인증코드 저장 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public boolean verifiedCode(String email, String authCode) throws Exception {
		try {
			String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
			return redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
		} catch (Exception e) {
			log.error("이메일 인증코드 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	// TODO: 2023-11-29  
	@Override
	public List<String> searchMember(String email, String nickName) throws Exception {
		repository.findAllByNickname(nickName);
		return null;
	}

	@Override
	public void deleteMember(String email) throws Exception {
		try {
			Member member = findMember(email);
			member.setStatus(0);
			repository.save(member);
		} catch (Exception e) {
			log.error("회원탈퇴 Error: " + e.getMessage());
			throw new RemoveException();
		}
	}

}
