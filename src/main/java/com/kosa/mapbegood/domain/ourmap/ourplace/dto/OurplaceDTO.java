package com.kosa.mapbegood.domain.ourmap.ourplace.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class OurplaceDTO {

	private Long id;

	private Long groupThememapId;
	
	private MemberDTO memberEmail;
	
	private PlaceDTO placeId;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date visitedAt;
	
}
