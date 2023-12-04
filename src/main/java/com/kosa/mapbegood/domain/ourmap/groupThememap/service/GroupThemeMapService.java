package com.kosa.mapbegood.domain.ourmap.groupThememap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.groupThememap.repository.GroupThememapRepository;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
import com.kosa.mapbegood.exception.FindException;


@Service
public class GroupThemeMapService {

	@Autowired
	private GroupsRepository groupsRepository;
	@Autowired
    private GroupThememapRepository groupThememapRepository;
	
	 /**
     * 테마지도 검색 
     * 설명: 그룹스 테이블에 id를 받아와서 그룹테마지도를 생성한다.
     * */
	
	  public GroupThememapDTO createGroupThememap(Long groupId, GroupThememapDTO groupThememapDTO) throws FindException {
	        Optional<Groups> optionalGroup = groupsRepository.findById(groupId);
	        Groups group = optionalGroup.orElseThrow(() -> new FindException("그룹을 찾을 수 없습니다."));

	        GroupThememap groupThememap = mapGroupThememapDTOToEntity(groupThememapDTO);
	        groupThememap.setGroupId(group.getId());

	        GroupThememap savedGroupThememap = groupThememapRepository.save(groupThememap);

	        return mapGroupThememapEntityToDTO(savedGroupThememap);
	    }
	  
	  /**
	     * 그룹 테마지도 삭제 
	     * 설명: 그룹스 테이블에 id를 받아와서 그룹테마지도를 삭제한다.
	 * @throws FindException 
	     * */
	  public void deleteGroupThememap(Long groupId,Long groupThememapId) throws FindException {
		  
		  Optional<Groups> optionalGroup=groupsRepository.findById(groupId);
	      Groups group = optionalGroup.orElseThrow(() -> new FindException("그룹을 찾을 수 없습니다."));

	      // 그룹 테마지도를 삭제하려면 그룹 ID도 일치해야 함
	        try {
				if (!GroupThememap.getGroupId().equals(groupId)) {
				    throw new FindException("그룹 테마지도를 삭제할 권한이 없습니다.");
				}
			} catch (FindException e) {
				e.printStackTrace();
			}

	        groupThememapRepository.deleteById(groupThememapId);
	    }
	        
	  
	  }
	  
	  // 그룹 테마지도 엔터티를 DTO로 변환
	    private GroupThememapDTO mapGroupThememapEntityToDTO(GroupThememap groupThememap) {
	        return GroupThememapDTO.builder()
	                .id(groupThememap.getId())
	                .groupId(groupThememap.getGroupId())
	                .name(groupThememap.getName())
	                .color(groupThememap.getColor())
	                .memo(groupThememap.getMemo())
	                .ourplaceList(groupThememap.getOurplaceList().stream()
	                        .map(ourplace -> OurplaceDTO.builder()
	                                .id(ourplace.getId())
	                                .name(ourplace.getName())
	                                // 필요한 필드 추가
	                                .build())
	                        .collect(Collectors.toList()))
	                .build();
	    }

	    // 그룹 테마지도 DTO를 엔터티로 변환
	    private GroupThememap mapGroupThememapDTOToEntity(GroupThememapDTO groupThememapDTO) {
	        GroupThememap groupThememap = new GroupThememap();
	        groupThememap.setId(groupThememapDTO.getId());
	        groupThememap.setGroupId(groupThememapDTO.getGroupId());
	        groupThememap.setName(groupThememapDTO.getName());
	        groupThememap.setColor(groupThememapDTO.getColor());
	        groupThememap.setMemo(groupThememapDTO.getMemo());

	        // 필요한 필드 추가

	        return groupThememap;
	    }
}
