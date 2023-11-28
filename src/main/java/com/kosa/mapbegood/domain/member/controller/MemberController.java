package com.kosa.mapbegood.domain.member.controller;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.mapper.MemberMapper;
import com.kosa.mapbegood.domain.member.service.MemberServiceInterface;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.security.response.ErrorResponse;
import com.kosa.mapbegood.util.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class MemberController {
//	@Autowired
//	private MemberMapper mapper;
	@Autowired
	private MemberServiceInterface service;
	@Autowired
	private AuthenticationUtil authenticationUtil;
	@Autowired
	private MemberMapper mapper;

	@PostMapping("/signup")
	public void createMember(@Valid @RequestBody MemberDTO.Post memberDTOPost) {
		log.error("MemberController: createMember()");
		Member member = mapper.MemberDTOPostToMember(memberDTOPost);
		service.createMember(member);
	}

	@GetMapping("/logout")
	public void logout() {
	}

	@GetMapping("/name")
	public void findNickName(MemberDTO.Nickname nickNameDTO) {
		try {
			service.findNickName(nickNameDTO.getNickname());
		} catch (Exception e) {
			log.error("MemberController: findNickName()" + e.getMessage());
//			return new ResponseEntity<>(, HttpStatus.OK);
		}
//		return new ResponseEntity<>(, HttpStatus.OK);
	}

	@PutMapping("/name")
	public void updateNickName(Authentication authentication, MemberDTO.Nickname nickNameDTO) {
		log.error("MemberController: updateNickName()");
		String email = authenticationUtil.getUserEmail(authentication);

		try {
			service.updateNickName(email, nickNameDTO.getNickname());
		} catch (Exception e) {
			log.error("MemberController: updateNickName()" + e.getMessage());
		}
	}

	@PutMapping("/pwd")
	public void updatePassword(Authentication authentication) {
		log.error("MemberController: updateNickName()");
		String email = authenticationUtil.getUserEmail(authentication);

		service.updatePassword();
	}

	@GetMapping("/pwd")
	public void findPassword() {
		service.findPassword();
	}

	@GetMapping("/user")
	public void searchMember(Authentication authentication) {
		service.searchUser();
	}

	@DeleteMapping("/user")
	public void deleteMember(Authentication authentication) {
		service.deleteUser();
	}
}
