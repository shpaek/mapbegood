package com.kosa.mapbegood.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosa.mapbegood.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	
//	Optional<Member> findMemberNickname(String nickname);
}
	