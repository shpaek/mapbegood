package com.kosa.mapbegood.domain.member.controller;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.service.MemberServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
//@Validated
public class MemberController {
//	@Autowired
//	private MemberMapper mapper;
	@Autowired
	private MemberServiceInterface service;

	@PostMapping("/signup")
	public void signup(@Valid @RequestBody MemberDTO.Post memberDTOPost) {
		log.error("MemberController: signup()");

		Member member = Member.builder()
				.nickname(memberDTOPost.getNickname())
				.email(memberDTOPost.getEmail())
				.password(memberDTOPost.getPassword())
				.build();

//		Member member = mapper.MemberDTOToMember(memberDTO);

		service.signup(member);
	}

	@GetMapping("/logout")
	public void logout() {
	}

	@PutMapping("/name")
	public void putNickName() {
		service.updateNickName();
	}

	@PutMapping("/pwd")
	public void putPassword() {
		service.updatePassword();
	}

	@GetMapping("/pwd")
	public void findPassword() {
		service.findPassword();
	}

	@GetMapping("/user")
	public void searchUser() {
		service.searchUser();
	}

	@DeleteMapping("/user")
	public void deleteUser() {
		service.deleteUser();
	}
}
