package com.kosa.mapbegood.domain.mymap.myplaceFeed.dto;

import java.time.LocalDateTime;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyplaceFeedDTO {

	private Long myplaceId;
	
	private MemberDTO memberEmail;
	
	private String content;
	
	private LocalDateTime createdAt;
}