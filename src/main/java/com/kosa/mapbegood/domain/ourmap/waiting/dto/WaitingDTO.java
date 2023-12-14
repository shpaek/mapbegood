package com.kosa.mapbegood.domain.ourmap.waiting.dto;

import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class WaitingDTO {
	private Long id;
	private Long groupId;
	private String memberEmail;
}
