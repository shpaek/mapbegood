package com.kosa.mapbegood.domain.ourmap.groups.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class GroupsService {
	@Autowired
	private GroupsRepository gr;
	
	/**
	 * GroupsDTO를 Groups(엔터티)로 변환한다
	 * @param groupsDto
	 * @return Groups
	 */
	public Groups groupsDtoToEntity(GroupsDTO groupsDto) {
		Groups entity = new Groups();
		entity.setId(groupsDto.getId());
		entity.setName(groupsDto.getName());
		entity.setLeaderNickname(groupsDto.getLeaderNickname());
		return entity;	
	}
	
	//vo->dto 그룹 
//	public List<GroupDTO> findAll() throws FindException{
//		그룹이랑 회원을 어떻게 연결해야될지 모르겠음 > 아...멤버그룹이랑 조인해서 해야함 흠...
//	}
	
	/**
	 * 사용자가 그룹을 생성한다
	 * @param groupsDto
	 * @throws AddException
	 */
	public void createGroup(GroupsDTO groupsDto) throws AddException{
		Groups entity = groupsDtoToEntity(groupsDto);
		gr.save(entity);
	}
	
	/**
	 * 그룹장이 그룹명을 수정한다
	 * @param groupsDto
	 * @throws ModifyException
	 */
	public void updateGroup(GroupsDTO groupsDto) throws ModifyException{
		Optional<Groups> optEntity = gr.findById(groupsDto.getId()); //내그룹찾기만들어야 엑박안뜸
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
	public void deleteGroup(Long groupId) throws RemoveException{
		gr.deleteById(groupId);
			//Long groupId, String leaderNickname) throws RemoveException{
//		Optional<Groups> optEntity = gr.findById(groupId);
//		optEntity.orElseThrow(()->
//			new RemoveException("그룹이 없어 삭제가 불가능합니다")
//	    );
//		Groups entity = optEntity.get();
//		if(entity.getLeaderNickname() == leaderNickname) {
//			gr.deleteById(groupId);			
//		}else {
//			new RemoveException("그룹장만 그룹을 삭제할 수 있습니다");
//		}
	}
}
