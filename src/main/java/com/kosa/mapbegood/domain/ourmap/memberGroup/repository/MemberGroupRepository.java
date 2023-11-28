package com.kosa.mapbegood.domain.ourmap.memberGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroupEmbedded;

public interface MemberGroupRepository  extends JpaRepository<MemberGroup, MemberGroupEmbedded>{
	@Query(nativeQuery=true,
		   value="DELETE member_group\r\n"
		   		+ "WHERE member_nickname=:memberNickname and group_id=:groupId")
	public int deleteMemberGroup(String memberNickname, Long groupId);
}
