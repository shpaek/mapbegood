package com.kosa.mapbegood.domain.ourmap.memberGroup.dto;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class MemberGroupDTO {
	private String memberEmail; 
	private Long groupId;
	private int leader;
}
