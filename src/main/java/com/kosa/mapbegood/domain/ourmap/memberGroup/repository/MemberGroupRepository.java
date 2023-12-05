package com.kosa.mapbegood.domain.ourmap.memberGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroupEmbedded;

public interface MemberGroupRepository  extends JpaRepository<MemberGroup, MemberGroupEmbedded>{
	
	public List<MemberGroup> findByMemberEmail(Member memberEmail);
	public List<MemberGroup> findByGroupId(Groups groupId);
//	public MemberGroup findByMemberEmailAndGroupId(Member memberEmail, Groups groupId);
}
