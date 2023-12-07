package com.kosa.mapbegood.domain.ourmap.groupThememap.dto;

import java.util.List;

import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class GroupThememapDTO {
	private Long id;
	private Long groupId;
	private String name;
	private String color;
	private String memo;
	private List<OurplaceDTO> ourplaceList;
}
