package com.kosa.mapbegood.domain.ourmap.groups.dto;

import java.util.List;

import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder 
//@ToString
public class GroupsDTO {
	private Long id;
	private String name;

	private List<WaitingDTO> waitingList;
	private List<GroupThememapDTO> groupThememapList;
	private List<MemberGroupDTO> memberGroupList;
	//ㄴGroups엔터티랑 같게 맞춰줌
}
