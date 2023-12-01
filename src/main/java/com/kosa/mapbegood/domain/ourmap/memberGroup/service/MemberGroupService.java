package com.kosa.mapbegood.domain.ourmap.memberGroup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroupEmbedded;
import com.kosa.mapbegood.domain.ourmap.memberGroup.repository.MemberGroupRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberGroupService {
	@Autowired
	MemberGroupRepository mgr;
	
	/**
	 * memberGroupDTO를 memberGroup(엔터티)로 변환한다
	 * @param memberGroupDto
	 * @return memberGroup
	 */
	public MemberGroup memberGroupDtoToEntity(MemberGroupDTO memberGroupDto) {
		//Embedded를 거쳐야 하는 경우
//		MemberGroupEmbedded em = new MemberGroupEmbedded();
//		em.setNickname(memberGroupDto.getMemberNickname());
//		em.setGroupsId(memberGroupDto.getGroupId());
//		entity.setId(em);
		MemberGroup memberGroupEntity = new MemberGroup();
		
		Member memberEntity = new Member();
		memberEntity.setEmail(memberGroupDto.getMember().getEmail());
		memberGroupEntity.setMemberEmail(memberEntity);
		Groups groupsEntity = new Groups();
		groupsEntity.setId(memberGroupDto.getGroups().getId());
		memberGroupEntity.setGroupId(groupsEntity);
		//memberEmail은 Member, groupId는 Groups타입이라 한 번 더 거치는 것
		memberGroupEntity.setLeader(memberGroupDto.getLeader());
		return memberGroupEntity;
	}
	

	/**
	 * List<MemberGroupDTO>를 반환한다
	 * @param groupId
	 * @return 그룹아이디에 해당하는 모든 그룹멤버의 이메일과 닉네임, 리더여부
	 * @throws FindException
	 */
	public List<MemberGroupDTO> findAllGroupMembersByGroupId(Long groupId) throws FindException{
		Groups group = new Groups();
		group.setId(groupId);
		try {
			List<MemberGroup> memberGroups = mgr.findByGroupId(group); //groupId에 해당하는 그룹멤버목록
			if(memberGroups==null) {
				throw new FindException("그룹의 멤버가 없습니다");
			}
			List<MemberGroupDTO> resultMemberGroupDTO = new ArrayList<>();
			for(int i=0; i<memberGroups.size(); i++) {
				//그룹멤버의 memberEmail의 맞는 멤버의 nickname찾아 맵핑
				MemberGroup membergroup = memberGroups.get(i); //groupId에 해당하는 그룹멤버 1명
				Member member = membergroup.getMemberEmail();
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setEmail(member.getEmail());
				memberDTO.setNickname(member.getNickname());
				
				MemberGroupDTO memberGroupDTO = new MemberGroupDTO(); //멤버정보를 그룹멤버로 가져옴
				memberGroupDTO.setMember(memberDTO);
				memberGroupDTO.setLeader(membergroup.getLeader()); //leader여부 넣어주기
				
				//매개변수로 받은 groupId를 그룹멤버에 넣어줌(그룹멤버 단일검색 때 MemberGroupDTO로 검색하기 위함) 
				GroupsDTO groupDTO = new GroupsDTO();
				groupDTO.setId(groupId);
				memberGroupDTO.setGroups(groupDTO);
				
				log.error("memberGroupDTO.getMember().getNickname()={}, memberGroupDTO.getGroups().getId()={}"
						, memberGroupDTO.getMember().getNickname(), memberGroupDTO.getGroups().getId());
				resultMemberGroupDTO.add(memberGroupDTO);
			}
			return resultMemberGroupDTO;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}
	}
	
	
	//그룹단일사용자검색(삭제용)
	/**
	 * MemberGroupDTO를 반환한다
	 * @param memberGroupDto
	 * @return 특정 그룹의 특정 멤버 한 명의 이메일, 닉네임, 리더여부, 그룹아이디를 반환한다
	 * @throws FindException
	 */
	/*public MemberGroupDTO findGroupMemberByMemberEmailAndGroupId(MemberGroupDTO memberGroupDto) throws FindException{
		//dto받아온 거 vo로 바꿔주고 각각의 vo에 넣어주고 mgs에 있는 메서드 사용하기! leader정보 잊지말기
		MemberGroup memberGroup = memberGroupDtoToEntity(memberGroupDto);
		Groups group = memberGroup.getGroupId();
		Member member = memberGroup.getMemberEmail();
		try {
			memberGroup = mgr.findByMemberEmailAndGroupId(member, group); //찾아온 정보로 다시 넣어주기
			
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setEmail(member.getEmail());
			memberDTO.setNickname(member.getNickname());
			
			MemberGroupDTO resultMemberGroupDTO = new MemberGroupDTO(); //멤버정보를 그룹멤버로 가져옴
			resultMemberGroupDTO.setMember(memberDTO);
			resultMemberGroupDTO.setLeader(memberGroup.getLeader());
			
			GroupsDTO groupDTO = new GroupsDTO(); //매개변수로 받은 그룹 정보 삽입
			groupDTO.setId(group.getId());
			resultMemberGroupDTO.setGroups(groupDTO);
			
			return resultMemberGroupDTO;			
		}catch(Exception e) {
			new FindException(e.getMessage());
			return null;
		}
	}*/
	
	
	/**
	 * 그룹의 멤버를 생성한다
	 * @param memberGroupDto
	 * @throws AddException
	 */
	public void createMemberGroup(MemberGroupDTO memberGroupDto) throws AddException{
		MemberGroup entity = memberGroupDtoToEntity(memberGroupDto);
		mgr.save(entity);
	}
	
	
	//멤버삭제 //멤버개인 찾기
	/**
	 * 그룹의 멤버를 삭제한다
	 * @param memberGroupDto
	 * @throws RemoveException
	 */
	public void deleteMemberGroup(MemberGroupDTO memberGroupDto) throws RemoveException{
		//findById로 MemberGroupEmbedded의 id값을 찾아서 MemberGroup의 id와 맞는 것을 지움
		if(memberGroupDto.getLeader()!=1) { //삭제되는 멤버가 리더가 아닐 때			
			MemberGroupEmbedded pk = new MemberGroupEmbedded();
			pk.setGroupsId(memberGroupDto.getGroups().getId());
			pk.setEmail(memberGroupDto.getMember().getEmail());
			Optional<MemberGroup> optMg = mgr.findById(pk);
			if(optMg.isPresent()) {
				MemberGroup mg = optMg.get();
				try {
					mgr.delete(mg);
				}catch(Exception e) {
					throw new RemoveException("그룹에서 제외될 수 없습니다");
				}
			}else {
				throw new RemoveException("그룹원이 아닌 사용자는 그룹에서 제외될 수 없습니다");
			}
		}else {
			throw new RemoveException("그룹장은 그룹에서 나갈 수 없습니다");
		}
	}

}
