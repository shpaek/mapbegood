package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, MemberCustomRepository {
    Optional<Member> findByNickname(String nickName);
    List<Member> findAllByNickname(String nickName);

    Optional<Member> findByEmail(String Email);
}
	