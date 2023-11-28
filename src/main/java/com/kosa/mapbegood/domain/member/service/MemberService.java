package com.kosa.mapbegood.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repository;
	
	 
	//public String findByNickname(String nickname) {
		
	//} 
	
	public void test() {
//		List<Member> opMem = repository.findAll();
		long n = repository.count();
		
//		System.out.println(opMem.size());
		System.out.println(n);
		
		
//		Member m = opMem.get(0);
//		if(opMem.isPresent()) {
//			Member m = opMem.get();
//			System.out.println(m.getEmail());
//			System.out.println(m.getNickname());
//		}
		
//		System.out.println("test");
	}

}
