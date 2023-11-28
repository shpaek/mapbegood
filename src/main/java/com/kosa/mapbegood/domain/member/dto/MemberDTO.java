package com.kosa.mapbegood.domain.member.dto;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class MemberDTO {
	private String nickname;
	private String email;
	private String password;
	private int status;
}
