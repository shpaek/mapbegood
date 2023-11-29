//package com.kosa.mapbegood.domain.ourmap.groups.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
//import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
//import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
//import com.kosa.mapbegood.domain.ourmap.memberGroup.repository.MemberGroupRepository;
//import com.kosa.mapbegood.exception.AddException;
//import com.kosa.mapbegood.exception.FindException;
//import com.kosa.mapbegood.exception.ModifyException;
//import com.kosa.mapbegood.exception.RemoveException;
//
//@Service
//public class GroupsService {
//	@Autowired
//	private GroupsRepository gr;
//	@Autowired
//	private MemberGroupRepository mgr;
//	
//	/**
//	 * GroupsDTO를 Groups(엔터티)로 변환한다
//	 * @param groupsDto
//	 * @return Groups
//	 */
//	public Groups groupsDtoToEntity(GroupsDTO groupsDto) {
//		Groups entity = new Groups();
//		entity.setId(groupsDto.getId());
//		entity.setName(groupsDto.getName());
//		entity.setLeaderEmail(groupsDto.getLeaderEmail());
//		return entity;	
//	}
//	
//	//vo->dto 내 그룹 전체검색, 그룹하나만 검색은 GroupService
//	//		  그룹에 해당하는 사용자들 검색은 MemberGroupService
//	//그룹 전체검색
//	/**
//	 * List<Groups>를 List<GroupsDTO>로 변환한다 (vo->DTO)
//	 * @param memberNickname
//	 * @return 내가 속한 모든 그룹
//	 * @throws FindException
//	 */
//	public List<GroupsDTO> findMembersAllGroup(String memberNickname) throws FindException{
//		List<Groups> groupsEntityList = gr.findMembersAllGroup(memberNickname);
//		List<GroupsDTO> groupsDtoList = new ArrayList<>();
//		
//		for(int i=0; i<groupsEntityList.size(); i++) {
//			Groups groupsEntity = groupsEntityList.get(i);
//			GroupsDTO groupsDto = GroupsDTO.builder()
//					.id(groupsEntity.getId())
//					.name(groupsEntity.getName())
//					.leaderEmail(groupsEntity.getLeaderEmail())
//					.build();
//			groupsDtoList.add(groupsDto);			
//		}
//		return groupsDtoList;
//	}
//	
//	
//	/**
//	 * 사용자가 그룹을 생성한다
//	 * @param groupsDto
//	 * @throws AddException
//	 */
//	public void createGroup(GroupsDTO groupsDto) throws AddException{
//		Groups entity = groupsDtoToEntity(groupsDto);
//		gr.save(entity);
//	}
//	
//	/**
//	 * 그룹장이 그룹명을 수정한다
//	 * @param groupsDto
//	 * @throws ModifyException
//	 */
//	public void updateGroup(GroupsDTO groupsDto) throws ModifyException{
//		//find로 그룹이 있는지 찾고 수정!
//		Optional<Groups> optEntity = gr.findById(groupsDto.getId()); 
//		optEntity.orElseThrow(()->
//			new ModifyException("그룹이 없어 수정이 불가능합니다")
//		);
//		Groups entity = optEntity.get();
//		entity.modifyGroupName(groupsDto.getName());
//		gr.save(entity);
//	}
//	
//	/**
//	 * 그룹장이 그룹을 삭제한다
//	 * @param groupId
//	 * @throws RemoveException
//	 */
//	public void deleteGroup(Long id) throws RemoveException{
//		//find로 그룹이 있는지 찾고 삭제!
//		Optional<Groups> optEntity = gr.findById(id);
//		if(optEntity.isPresent()) {
//			try {
//				gr.deleteById(id);							
//			}catch(Exception e) {
//				new RemoveException(e.getMessage());
//			}
//		}else {
//			new RemoveException("그룹이 없어 삭제가 불가능합니다");
//		}
//			//Long groupId, String leaderNickname) throws RemoveException{
////		Optional<Groups> optEntity = gr.findById(groupId);
////		optEntity.orElseThrow(()->
////			new RemoveException("그룹이 없어 삭제가 불가능합니다")
////	    );
////		Groups entity = optEntity.get();
////		if(entity.getLeaderNickname() == leaderNickname) {
////			gr.deleteById(groupId);			
////		}else {
////			new RemoveException("그룹장만 그룹을 삭제할 수 있습니다");
////		}
//	}
//}
