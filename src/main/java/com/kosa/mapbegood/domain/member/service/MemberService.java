package com.kosa.mapbegood.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.mapbegood.domain.common.service.AwsS3Service;
import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.dto.MemberInfoDTO;
import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.dto.MemberUpdateDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.mapper.MemberMapper;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.service.ThemeMapService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
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
	private final AwsS3Service awsS3Service;
	private final MemberMapper mapper;
	private final String profileImageUploadPath = "/profile-image";
	private final String LOGIN_CACHE = "login-cache ";
	private final Long DURATION_CACHE = 600000L;

	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	@Override
	public MemberInfoDTO findLoginInfo(String email) throws Exception {
		String loginInfo = redisService.getValues(LOGIN_CACHE + email);
		ObjectMapper objectMapper = new ObjectMapper();

		if (!redisService.checkExistsValue(loginInfo)) {
			Member member = this.findMember(email);
			MemberInfoDTO memberInfoDto = memberToInfoDto(member);
			this.loginInfoRedisSave(memberInfoDto);
			return memberInfoDto;
		} else {
			return objectMapper.readValue(loginInfo, MemberInfoDTO.class);
		}
	}

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

	@Transactional
	@Override
	public void createMember(Member member, MultipartFile profileImage) throws Exception {
		try {
			Optional<Member> optMember = repository.findById(member.getEmail());
			if (optMember.isEmpty()) {
				if (!Objects.isNull(profileImage)) {
					String imageUrl = awsS3Service.uploadImage(profileImage, profileImageUploadPath);
					member.setProfileImage(imageUrl);
				}
				member.setPassword(pwEncoder.encode(member.getPassword()));
				member.setStatus(1L);
				repository.save(member);
			} else {
				throw new AddException("이미 가입된 계정이 있습니다.");
			}
		} catch (AddException ae) {
			log.error(ae.getMessage());
			throw ae;
		} catch (Exception e) {
			log.error("회원가입 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public void duplicationNickName(String nickName) throws Exception {
		try {
			Optional<Member> findMember = repository.findByNickname(nickName);
			if (nickName.isEmpty() || findMember.isPresent()) {
				throw new AddException("해당 닉네임은 이미 사용중입니다.");
			}
		} catch (AddException ae) {
			log.error(ae.getMessage());
			throw ae;
		} catch (Exception e) {
			log.error("닉네임 중복 확인 Error: " + e.getMessage());
			throw new AddException();
		}
	}

	@Override
	public void verifyPassword(String email, String password) throws Exception {
		try {
			Member member = this.findMember(email);
			if (!pwEncoder.matches(password, member.getPassword())) {
				throw new Exception();
			};
		} catch (Exception e) {
			log.error("패스워드 검증 실패: " + e.getMessage());
			throw new Exception();
		}
	}

	@Transactional
	@Override
	public void updateNickName(String email, String nickName) throws Exception {
		try {
			Member member = this.findMember(email);
			member.setNickname(nickName);
			repository.save(member);

			MemberInfoDTO memberInfoDTO = this.memberToInfoDto(member);
			this.loginInfoRedisSave(memberInfoDTO);
		} catch (Exception e) {
			log.error("닉네임 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Override
	public void updatePassword(String email, String password) throws Exception {
		try {
			Member member = this.findMember(email);
			member.setPassword(pwEncoder.encode(password));
			repository.save(member);
		} catch (Exception e) {
			log.error("패스워드 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Transactional
	@Override
	public void updateProfileImage(String email, MultipartFile profileImage) throws Exception{
		try {
			String imageUrl = awsS3Service.uploadImage(profileImage, profileImageUploadPath);
			Member member = this.findMember(email);
			member.setProfileImage(imageUrl);
			repository.save(member);

			MemberInfoDTO memberInfoDTO = this.memberToInfoDto(member);
			this.loginInfoRedisSave(memberInfoDTO);
		} catch (Exception e) {
			log.error("프로필 이미지 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Transactional
	@Override
	public void updateMyInfo(String email, MemberUpdateDTO memberUpdateDto, MultipartFile updateProfileImage) throws Exception {
		try {
			Member member = this.findMember(email);
			if (!Objects.isNull(memberUpdateDto.getNickname())) {
				member.setNickname(memberUpdateDto.getNickname());
				log.error("nick은 널?: " + Objects.isNull(memberUpdateDto.getNickname()));
			}
			if (!Objects.isNull(memberUpdateDto.getPassword())) {
				member.setPassword(pwEncoder.encode(memberUpdateDto.getPassword()));
				log.error("pwd: " + member.getPassword());
			}
			if (!Objects.isNull(updateProfileImage)) {
				String imageUrl = awsS3Service.uploadImage(updateProfileImage, profileImageUploadPath);
				member.setProfileImage(imageUrl);
				log.error("img: " + member.getProfileImage());
			}
			repository.save(member);

			MemberInfoDTO memberInfoDTO = this.memberToInfoDto(member);
			this.loginInfoRedisSave(memberInfoDTO);
		} catch (Exception e) {
			log.error("회원정보 수정 Error: " + e.getMessage());
			throw new ModifyException();
		}
	}

	@Transactional
	@Override
	public void sendCodeToEmail(String email) throws Exception {
		String nickName = null;
		try {
			Member member = this.findMember(email);
			nickName = member.getNickname();
		} catch (Exception e) {
			nickName = email.substring(0, email.indexOf("@"));
		} finally {
			String title = "[좋을지도] " + nickName + " 님 인증번호 안내드립니다.";
			String authCode = RandomStringUtils.randomAlphanumeric(10);
			this.sendEmail(email, title, authCode, nickName);
			this.saveAuthCode(email, authCode);
		}
	}

	@Override
	public boolean verifiedCode(String email, String authCode) throws Exception {
		try {
			String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
			if (redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode)) {
				redisService.deleteValues(AUTH_CODE_PREFIX + email);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("이메일 인증코드 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public List<MemberInfoDTO> searchMember(String email, String nickName) throws Exception {
		this.findMember(email);
		return repository.memberSearch(nickName);
	}

	@Override
	public void deleteMember(String email) throws Exception {
		try {
			Member member = this.findMember(email);
			member.setStatus(0L);
			repository.save(member);
		} catch (Exception e) {
			log.error("회원탈퇴 Error: " + e.getMessage());
			throw new RemoveException();
		}
	}

	private void loginInfoRedisSave(MemberInfoDTO memberInfoDto) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String memberInfoJson = objectMapper.writeValueAsString(memberInfoDto);
		redisService.setValues(LOGIN_CACHE + memberInfoDto.getEmail(), memberInfoJson, Duration.ofMillis(DURATION_CACHE));
	}

	private void sendEmail(String email, String title, String authCode, String nickName) throws Exception {
		try {
			mailService.sendEmail(email, title, authCode, nickName);
		} catch (Exception e) {
			log.error("이메일 전송 Error: " + e.getMessage());
			throw new Exception();
		}
	}

	private void saveAuthCode(String email, String authCode) throws Exception {
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

	private MemberInfoDTO memberToInfoDto(Member member) {
		return mapper.MemberToMemberInfoDTO(member);
	}
}