package com.kosa.mapbegood.domain.ourmap.waiting.dto;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class WaitingDTO {
	private Long id;
	private Long groupId;
	private String memberEmail;
}
