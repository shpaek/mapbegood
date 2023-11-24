package com.kosa.mapbegood.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repository;
	
	public void test() {
		long n = repository.count();
		System.out.println(n);
		log.error("MemberService: test()");
	}

}
