package com.kosa.mapbegood.domain.ourmap.groupThememap.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.groupThememap.repository.GroupThememapRepository;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
import com.kosa.mapbegood.exception.FindException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GroupThemeMapService {

	@Autowired
	private GroupsRepository groupsRepository;
	@Autowired
    private GroupThememapRepository groupThememapRepository;
	
	 /**
     * 그룹테마지도 생성o
     * 설명: 그룹스 테이블에 id를 적어서 groupthememap을 생성한다.
     * */
	
	public GroupThememapDTO createGroupThememap(Long groupId, GroupThememapDTO groupThememapDTO) throws FindException {
        Optional<Groups> optionalGroup = groupsRepository.findById(groupId);
        Groups group = optionalGroup.orElseThrow(() -> new FindException("그룹을 찾을 수 없습니다."));

        GroupThememap groupThememap = mapGroupThememapDTOToEntity(groupThememapDTO);
        groupThememap.setGroupId(group); // groupId를 URL에서 받아온 값으로 설정

        GroupThememap savedGroupThememap = groupThememapRepository.save(groupThememap);

        return mapGroupThememapEntityToDTO(savedGroupThememap);
    }
	  
	  /**
	     * 그룹 테마지도 삭제  o
	     * 설명:  그룹테마지도 id를 이용해서 그 그룹테마지도 id에 해당하는 그룹테마지도를 삭제한다.
	 * @throws FindException 
	     * */
	  
	  public void deleteGroupThememap(Long groupId,Long groupThememapId) throws FindException {
		  
		  Optional<Groups> optionalGroup = groupsRepository.findById(groupId);
		  Groups group = optionalGroup.orElseThrow(() -> new FindException("그룹을 찾을 수 없습니다."));

		    // 그룹 테마지도를 삭제하려면 그룹 ID도 일치해야 함
		    Optional<GroupThememap> optionalGroupThememap = groupThememapRepository.findById(groupThememapId);
		    GroupThememap groupThememap = optionalGroupThememap.orElseThrow(() -> new FindException("그룹 테마지도를 찾을 수 없습니다."));

	        groupThememapRepository.deleteById(groupThememapId);
	    }
	  
	  
	  /**
	     * 그룹 테마지도 수정 o
	     * 설명: 그룹스 테이블에 id를 받아와서 그룹테마지도 수정.
	 * @throws FindException 
	     * */     
	 
	  public GroupThememapDTO updateGroupThememap(Long groupId,Long groupThememapId,GroupThememapDTO groupthemeMapDto ) throws FindException {
		  try {
		        // Optional<GroupThememap> optionalGroupThememap = groupThememapRepository.findById(groupId);
		        Optional<Groups> optionalGroup = groupsRepository.findById(groupId);

		        if (optionalGroup.isPresent()) {
		            Groups group = optionalGroup.get();

		            // 업데이트할 그룹 테마지도를 찾기 위해 groupId와 groupThememapId를 활용
		            Optional<GroupThememap> optionalGroupThememap = groupThememapRepository.findById(groupThememapId);

		            if (optionalGroupThememap.isPresent()) {
		                GroupThememap groupThememap = optionalGroupThememap.get();
		                // 업데이트할 내용을 DTO에서 가져와서 엔터티에 설정
		                groupThememap.setName(groupthemeMapDto.getName());
		                groupThememap.setColor(groupthemeMapDto.getColor());
		                groupThememap.setMemo(groupthemeMapDto.getMemo());
		                // 엔터티 저장한 다음 업데이트된 엔터리 반환
		                GroupThememap updatedGroupThememap = groupThememapRepository.save(groupThememap);
		                return mapGroupThememapEntityToDTO(updatedGroupThememap);
		            } else {
		                throw new FindException("그룹 테마지도를 찾을 수 없습니다");
		            }
		        } else {
		            throw new FindException("그룹을 찾을 수 없습니다.");
		        }
		    } catch (Exception e) {
		        throw new FindException("그룹 테마지도를 찾을 수 없습니다");
		    }
		}
	  
	  /**
	   * 
	   * 모든그룹테미지도 조회 o
	   * 설명: 그룹스테이블에 그룹아이디를 받아와서 그 그룹아이디에 해당하는 그룹테마지도들을 조회
	   * */
	  public List<GroupThememapDTO> getAllGroupThememaps(Long groupId) throws FindException {
		    try {
		    	Optional<Groups> optionalGroup = groupsRepository.findById(groupId);
		        
		        if (optionalGroup.isPresent()) {
		            Groups group = optionalGroup.get();
		            List<GroupThememap> groupThememaps = group.getGroupThememapList();
		            			        
		            // 조회된 엔터티 리스트를 DTO 리스트로 변환하여 반환
		            return groupThememaps.stream()
		                    .map(this::mapGroupThememapEntityToDTO)
		                    .collect(Collectors.toList());
		        } else {
		            throw new FindException("그룹이 없습니다.");
		        }
		        
		    } catch (Exception e) {
		    	e.printStackTrace();
		        throw new FindException("그룹 테마지도 조회 중 오류가 발생했습니다.");
		    }
		}
	 
	  //groupid와 groupthememapid를 받아서 상세조회하기

	  
	  
	  /**
	     * 그룹 테마지도 상세 조회
	     * 설명: 그룹 ID와 그룹 테마지도 ID를 사용하여 특정 그룹 테마지도를 조회한다.
	     */
	    public GroupThememapDTO getGroupThememapDetail( Long groupThememapId) throws FindException {
	        try {
//	            Optional<GroupThememap> optionalGroupThememap = groupThememapRepository.findByIdAndGroupId(groupThememapId, groupId);
	        	Optional<GroupThememap> optionalGroupThememap = 
	        			groupThememapRepository.findById(groupThememapId);
	            
	        	if (optionalGroupThememap.isPresent()) {
	                GroupThememap groupThememap = optionalGroupThememap.get();
	                return mapGroupThememapEntityToDTO(groupThememap);
	            } else {
	                throw new FindException("그룹 테마지도를 찾을 수 없습니다");
	            }
	        } catch (Exception e) {
	            throw new FindException("그룹 테마지도 조회 중 오류가 발생했습니다.");
	        }
	    }
//	// 그룹테마지도 조회 by 그룹 ID 및 그룹테마맵 ID o
//	  public GroupThememapDTO getGroupThememap(Long groupId, Long groupThememapId) throws FindException {
//	      try {
//	          Optional<Groups> optionalGroup = groupsRepository.findById(groupId);
//	          if (optionalGroup.isPresent()) {
//	              Groups group = optionalGroup.get();
//	              Optional<GroupThememap> optionalGroupThememap = groupThememapRepository.findByIdAndGroupId(groupThememapId, group.getId());
//
//	              if (optionalGroupThememap.isPresent()) {
//	                  GroupThememap groupThememap = optionalGroupThememap.get();
//	                  return mapGroupThememapEntityToDTO(groupThememap);
//	              } else {
//	                  throw new FindException("그룹 테마지도를 찾을 수 없습니다");
//	              }
//	          } else {
//	              throw new FindException("그룹을 찾을 수 없습니다.");
//	          }
//	      } catch (Exception e) {
//	          throw new FindException("그룹 테마지도 조회 중 오류가 발생했습니다.");
//	      }
//	  }
 
	  // 그룹 테마지도 엔터티를 DTO로 변환
	    public GroupThememapDTO mapGroupThememapEntityToDTO(GroupThememap groupThememap) {
	        return GroupThememapDTO.builder()
	                .id(groupThememap.getId())
	                .groupId(groupThememap.getGroupId().getId())
	                .name(groupThememap.getName())
	                .color(groupThememap.getColor())
	                .memo(groupThememap.getMemo())
               
	                .build();
	    }

	    // 그룹 테마지도 DTO를 엔터티로 변환 
	    private GroupThememap mapGroupThememapDTOToEntity(GroupThememapDTO groupThememapDTO) {
	    	Groups group = new Groups();
	    	group.setId(groupThememapDTO.getGroupId());
	        GroupThememap groupThememap = new GroupThememap();
	        groupThememap.setId(groupThememapDTO.getId());
	        groupThememap.setGroupId(group);
	        groupThememap.setName(groupThememapDTO.getName());
	        groupThememap.setColor(groupThememapDTO.getColor());
	        groupThememap.setMemo(groupThememapDTO.getMemo());

	        // 필요한 필드 추가

	        return groupThememap;
	    }
}
