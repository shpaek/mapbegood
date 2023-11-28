package com.kosa.mapbegood.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>{

    Optional<Member> findByEmail(String email);

}
	