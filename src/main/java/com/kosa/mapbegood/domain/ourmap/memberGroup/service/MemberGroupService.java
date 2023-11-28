package com.kosa.mapbegood.domain.ourmap.memberGroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.repository.MemberGroupRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class MemberGroupService {
	@Autowired
	MemberGroupRepository mgr;
	
	//dto -> vo
	public MemberGroup memberGroupDtoToEntity(MemberGroupDTO memberGroupDto) {
		MemberGroup memberGroupEntity = new MemberGroup();
		//Embedded를 거쳐야 하는 경우
//		MemberGroupEmbedded em = new MemberGroupEmbedded();
//		em.setNickname(memberGroupDto.getMemberNickname());
//		em.setGroupsId(memberGroupDto.getGroupId());
//		entity.setId(em);
		Member memberEntity = new Member();
		memberEntity.setNickname(memberGroupDto.getMemberNickname());
		memberGroupEntity.setMemberNickname(memberEntity);
		
		Groups groupsEntity = new Groups();
		groupsEntity.setId(memberGroupDto.getGroupId());
		memberGroupEntity.setGroupId(groupsEntity);
		
		memberGroupEntity.setLeader(memberGroupDto.getLeader());
		return memberGroupEntity;
	}
	
	//vo -> dto
	//1. 전체그룹 보기
	//2. 선택그룹보기를 그룹서비스에서 해야되나 고민중

	//멤버 생성 (그룹장/일반=>컨트롤러에서 이거 셋팅값 바꿔주기)
	public void createMemberGroup(MemberGroupDTO memberGroupDto) throws AddException{
		MemberGroup entity = memberGroupDtoToEntity(memberGroupDto);
		mgr.save(entity);
	}
	
	//멤버삭제
	public void deleteMemberGroup(MemberGroupDTO memberGroupDto) throws RemoveException{
		if(memberGroupDto.getLeader() == false) {
			new RemoveException("그룹장은 멤버에서 삭제될 수 없습니다");
		}else {
			int result = mgr.deleteMemberGroup(memberGroupDto.getMemberNickname(), memberGroupDto.getGroupId());
			if(result != 1) {
				new RemoveException("그룹의 멤버 삭제 실패");
			}
		}
	}
}
