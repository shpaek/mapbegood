package com.kosa.mapbegood.domain.ourmap.groups.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.repository.MemberGroupRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GroupsService {
	@Autowired
	private GroupsRepository gr;
	@Autowired
	private MemberGroupRepository mgr;
	
	/**
	 * GroupsDTO를 Groups(엔터티)로 변환한다
	 * @param groupsDto
	 * @return Groups
	 */
	public Groups groupsDtoToEntity(GroupsDTO groupsDto) {
		Groups entity = new Groups();
		entity.setId(groupsDto.getId());
		entity.setName(groupsDto.getName());
//		entity.setLeaderEmail(groupsDto.getLeaderEmail());
		return entity;	
	}
	
	//vo->dto 내 그룹 전체검색, 그룹하나만 검색은 GroupService
	//		  그룹에 해당하는 사용자들 검색은 MemberGroupService
	//그룹 전체검색
	/**
	 * List<GroupsDTO>를 반환한다
	 * @param memberEmail
	 * @return 이메일 사용자가 속한 모든 그룹의 그룹명과 그룹장의 닉네임
	 * @throws FindException
	 */
	public List<GroupsDTO> findAllGroupsByMemberEmail(String memberEmail) throws FindException{
		Member member = new Member();		
		member.setEmail(memberEmail);
		try {
			List<MemberGroup> memberGroups = mgr.findByMemberEmail(member);//이메일 사용자가 속해있는 멤버그룹들
			List<GroupsDTO> resultGroupDTO = new ArrayList<>();//이메일 사용자가 속한 그룹들
			for(int i=0; i<memberGroups.size(); i++) {
				//그룹정보 셋팅
				MemberGroup membergroup = memberGroups.get(i); //이메일 사용자가 속해있는 그룹1개
				Groups group = membergroup.getGroupId();
				GroupsDTO groupDTO = new GroupsDTO();
				groupDTO.setId(group.getId());
				groupDTO.setName(group.getName());
				
				List<MemberGroup>members = group.getMemberGroupList(); //각 그룹의 멤버들
				for(MemberGroup mg: members) {
					if(mg.getLeader() == 1) { //리더인 경우
						//String leaderNickName = members.get(0).getMemberEmail().getNickname();
						MemberDTO leaderMemberDTO = new MemberDTO();
						leaderMemberDTO.setNickname( members.get(0).getMemberEmail().getNickname());
		
						MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
						memberGroupDTO.setMember(leaderMemberDTO);
						
						List<MemberGroupDTO> listGroupMemberDTO = new ArrayList<>();
						listGroupMemberDTO.add(memberGroupDTO);
						groupDTO.setMemberGroupList(listGroupMemberDTO);
					}			
				}
				
				log.error("groupDTO.getId={}, groupDTO.getName={},leaderNickname={} ", groupDTO.getId(), groupDTO.getName(), groupDTO.getMemberGroupList().get(0).getMember().getNickname());
				resultGroupDTO.add(groupDTO);
			}
			return resultGroupDTO;
		}catch(Exception e) {
			new FindException(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 사용자가 그룹을 생성한다
	 * @param groupsDto
	 * @throws AddException
	 */
	public void createGroup(GroupsDTO groupsDto) throws AddException{
		Groups entity = groupsDtoToEntity(groupsDto);
		gr.save(entity); //사용자가 그룹을 생성하면 그룹을 생성한 사용자가 자동으로 그룹멤버의 그룹장으로 추가되어야함
		MemberGroup mgEntity = new MemberGroup();
		mgEntity.setGroupId(entity);
		Member mEntity = new Member();
		mEntity.setEmail(entity.getMemberGroupList().get(0).getMemberEmail().getEmail());
		mgEntity.setMemberEmail(mEntity);
		mgr.save(mgEntity);
		
	}
	
	/**
	 * 그룹장이 그룹명을 수정한다
	 * @param groupsDto
	 * @throws ModifyException
	 */
	public void updateGroup(GroupsDTO groupsDto) throws ModifyException{
		//find로 그룹이 있는지 찾고 수정!
		Optional<Groups> optEntity = gr.findById(groupsDto.getId()); 
		optEntity.orElseThrow(()->
			new ModifyException("그룹이 없어 수정이 불가능합니다")
		);
		Groups entity = optEntity.get();
		entity.modifyGroupName(groupsDto.getName());
		gr.save(entity);
	}
	
	/**
	 * 그룹장이 그룹을 삭제한다
	 * @param groupId
	 * @throws RemoveException
	 */
	public void deleteGroup(Long id) throws RemoveException{
		//find로 그룹이 있는지 찾고 삭제!
		Optional<Groups> optEntity = gr.findById(id);
		if(optEntity.isPresent()) {
			try {
				gr.deleteById(id);							
			}catch(Exception e) {
				new RemoveException(e.getMessage());
			}
		}else {
			new RemoveException("그룹이 없어 삭제가 불가능합니다");
		}
	}
}
