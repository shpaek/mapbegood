package com.kosa.mapbegood.domain.ourmap.groups.dto;

import java.util.List;

import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class GroupsDTO {
	private Long id;
	private String name;
	private String leaderNickname;
	private List<WaitingDTO> waitingList;
	private List<GroupThememapDTO> groupThememapList;
}
