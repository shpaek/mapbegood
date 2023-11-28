package com.kosa.mapbegood.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	static Member findByNickname(String nickname) {
		 
		return null;
	}
}
	