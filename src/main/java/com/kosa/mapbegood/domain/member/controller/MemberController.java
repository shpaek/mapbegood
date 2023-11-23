package com.kosa.mapbegood.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.service.MemberService;

@RestController
public class MemberController {
	
//	@Autowired
	private MemberService service;
	
	public MemberController() {
		this.service = new MemberService();
	}
	
	@GetMapping("/test")
	public void get() {
		service.test();
	}

}
