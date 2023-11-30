package com.kosa.mapbegood.domain.member.controller;

import com.kosa.mapbegood.domain.common.response.Response;
import com.kosa.mapbegood.domain.member.dto.MemberNickNameDTO;
import com.kosa.mapbegood.domain.member.dto.MemberPassDTO;
import com.kosa.mapbegood.domain.member.dto.MemberSignUpDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.mapper.MemberMapper;
import com.kosa.mapbegood.domain.member.service.MemberServiceInterface;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class MemberController {
	@Autowired
	private MemberServiceInterface service;
	@Autowired
	private AuthenticationUtil authenticationUtil;
	@Autowired
	private MemberMapper mapper;

	@PostMapping("/signup")
	public ResponseEntity createMember(@Valid @RequestBody MemberSignUpDTO signUpDto) {
		try {
			Member member = mapper.MemberDTOPostToMember(signUpDto);
			service.createMember(member);
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "회원가입에 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "회원가입이 완료되었습니다."), HttpStatus.OK);
	}

	// TODO: 2023-11-29
	@GetMapping("/logout")
	public void logout() {
	}

	@GetMapping("/name")
	public ResponseEntity duplicationNickName(@Valid MemberNickNameDTO nickNameDto) {
		try {
			service.duplicationNickName(nickNameDto.getNickname());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "중복된 닉네임 입니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "사용 가능한 닉네임 입니다."), HttpStatus.OK);
	}

	@PutMapping("/name")
	public ResponseEntity updateNickName(Authentication authentication, @Valid MemberNickNameDTO nickNameDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.updateNickName(email, nickNameDto.getNickname());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "닉네임 수정이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "닉네임이 수정되었습니다."), HttpStatus.OK);
	}

	@PutMapping("/pwd")
	public ResponseEntity updatePassword(Authentication authentication, @Valid MemberPassDTO passwordDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			service.updatePassword(email, passwordDto.getPassword());
		} catch (Exception e) {
			return new ResponseEntity<>(new Response(0, "패스워드 변경이 실패했습니다."), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(1, "패스워드가 변경되었습니다."), HttpStatus.OK);
	}

	// TODO: 2023-11-29
	@GetMapping("/pwd")
	public void findPassword() {
		service.findPassword();
	}

	// TODO: 2023-11-29
	@GetMapping("/user")
	public ResponseEntity searchMember(Authentication authentication, @Valid MemberNickNameDTO nicknameDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			List<String> nickNameList = service.searchMember(email, nicknameDto.getNickname());
		} catch (FindException e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

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
}
