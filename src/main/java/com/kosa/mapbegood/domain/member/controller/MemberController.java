package com.kosa.mapbegood.domain.member.controller;

import com.kosa.mapbegood.domain.common.response.Response;
import com.kosa.mapbegood.domain.member.dto.*;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.mapper.MemberMapper;
import com.kosa.mapbegood.domain.member.service.MemberServiceInterface;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.security.refresh.RefreshTokenService;
import com.kosa.mapbegood.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberServiceInterface service;
	private final AuthenticationUtil authenticationUtil;
	private final MemberMapper mapper;
	private final RefreshTokenService refreshTokenService;

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
	public ResponseEntity duplicationNickName(@Valid @RequestBody MemberNickNameDTO nickNameDto) {
		try {
			service.duplicationNickName(nickNameDto.getNickname());
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

	// TODO: 2023-11-30
	// 사용자 검색
	@GetMapping("/user")
	public ResponseEntity searchMember(Authentication authentication,
									   @Valid MemberNickNameDTO nicknameDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			List<String> nickNameList = service.searchMember(email, nicknameDto.getNickname());
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
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
