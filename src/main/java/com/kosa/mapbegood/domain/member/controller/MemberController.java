package com.kosa.mapbegood.domain.member.controller;

import java.util.List;

import javax.validation.Valid;

import com.kosa.mapbegood.domain.common.service.RedisService;
import com.kosa.mapbegood.domain.member.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosa.mapbegood.domain.common.response.Response;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.mapper.MemberMapper;
import com.kosa.mapbegood.domain.member.service.MemberServiceInterface;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.security.refresh.RefreshTokenService;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberServiceInterface service;
	private final AuthenticationUtil authenticationUtil;
	private final MemberMapper mapper;
	private final RefreshTokenService refreshTokenService;

	// 로그인 정보
	@GetMapping("/login-info")
	public ResponseEntity getLoginInfo(Authentication authentication) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			MemberInfoDTO memberInfoDTO = service.findLoginInfo(email);
        	return new ResponseEntity<>(memberInfoDTO, HttpStatus.OK);
        } catch (Exception e) {
			log.error("로그인 사용자 Redis 조회 Error: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
	}

	// 회원 가입
	@PostMapping("/signup")
	public ResponseEntity createMember(@Valid @RequestBody MemberSignUpDTO signUpDto) {
		try {
			Member member = mapper.MemberDTOPostToMember(signUpDto);
			service.createMember(member);
		} catch (AddException ae) {
			return new ResponseEntity<>(new Response(0, "이미 가입된 계정이 있습니다."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "회원가입에 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "회원가입이 완료되었습니다."), HttpStatus.CREATED);
	}

	// 닉네임 중복 확인
	@GetMapping("/name")
	public ResponseEntity duplicationNickName(@Valid @RequestParam("nickName") String nickName) {
		try {
			service.duplicationNickName(nickName);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "중복된 닉네임 입니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "사용 가능한 닉네임 입니다."), HttpStatus.OK);
	}

	// 비밀번호 인증
	@PostMapping("/pwd")
	public ResponseEntity verifyPassword(Authentication authentication,
										 @Valid @RequestBody MemberPassDTO passDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.verifyPassword(email, passDto.getPassword());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "패스워드가 다릅니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "패스워드 확인"), HttpStatus.OK);
	}

	// 닉네임 수정
	@PutMapping("/name")
	public ResponseEntity updateNickName(Authentication authentication,
										 @Valid @RequestBody MemberNickNameDTO nickNameDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.updateNickName(email, nickNameDto.getNickname());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "닉네임 수정이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "닉네임이 수정되었습니다."), HttpStatus.OK);
	}

	// 비밀번호 변경
	@PutMapping("/pwd")
	public ResponseEntity updatePassword(Authentication authentication,
										 @Valid @RequestBody MemberPassDTO passwordDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.updatePassword(email, passwordDto.getPassword());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "패스워드 변경이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "패스워드가 변경되었습니다."), HttpStatus.OK);
	}

	// 프로필 이미지 수정
	@PutMapping("/profile-image")
	public ResponseEntity updateProfileImage(Authentication authentication,
											 @RequestPart(value="profileImage") MultipartFile profileImage) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.updateProfileImage(email, profileImage);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "프로필 사진 변경이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "프로필 사진이 변경되었습니다."), HttpStatus.OK);
	}

	// 비밀번호 찾기(이메일 전송)
	@PostMapping("/auth-email")
	public ResponseEntity sendMessage(@Valid @RequestBody MemberEmailDTO emailDto) {
		try {
			service.sendCodeToEmail(emailDto.getEmail());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "이메일 전송이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "인증번호가 이메일로 전송되었습니다."), HttpStatus.OK);
	}

	// 비밀번호 찾기(문자일 인증)
	@PostMapping("/auth-code")
	public ResponseEntity verificationEmail(@Valid @RequestBody MemberEmailVerifyDTO emailVerifyDto) {
		try {
			if (service.verifiedCode(emailVerifyDto.getEmail(), emailVerifyDto.getCode())) {
				MultiValueMap<String, String> headers = this.addHeaderTempAccessToken(emailVerifyDto.getEmail());
				return new ResponseEntity<>(new Response(1, "인증번호가 일치합니다."), headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Response(0, "인증에 실패했습니다."), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "인증에 실패했습니다."), HttpStatus.OK);
		}
	}

	// 사용자 검색
	@GetMapping("/user")
	public ResponseEntity<List<MemberSearchResponseDTO>> searchMember(Authentication authentication,
																	  @Valid @RequestParam("nickName") String nickName) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			List<MemberSearchResponseDTO> memberSearchResult = service.searchMember(email, nickName);
			return ResponseEntity.ok(memberSearchResult);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}

	// 회원 탈퇴
	@DeleteMapping("/user")
	public ResponseEntity deleteMember(Authentication authentication) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.deleteMember(email);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "회원탈퇴에 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "탈퇴 되었습니다."), HttpStatus.OK);
	}

	private MultiValueMap<String, String> addHeaderTempAccessToken(String email) throws Exception {
		Member findMember = service.findMember(email);
		String tempAccessToken = refreshTokenService.delegateAccessToken(findMember);

		MultiValueMap<String, String> tempAccessTokenHeader = new LinkedMultiValueMap<>();
		tempAccessTokenHeader.add("Authorization", tempAccessToken);

		return tempAccessTokenHeader;
	}
}
