package com.kosa.mapbegood.domain.ourmap.memberGroup.dto;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class MemberGroupDTO {
	private MemberDTO member; 
	private GroupsDTO groups;
	private int leader;
}
