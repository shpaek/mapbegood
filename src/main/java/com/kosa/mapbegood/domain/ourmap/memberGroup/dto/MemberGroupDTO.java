package com.kosa.mapbegood.domain.ourmap.memberGroup.dto;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class MemberGroupDTO {
	private MemberDTO member; 
	private GroupsDTO groups;
	private int leader;
}
