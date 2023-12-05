package com.kosa.mapbegood.domain.mymap.myplaceFeed.dto;

import com.kosa.mapbegood.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyplaceFeedDTO {

	private Long myplaceId;
	
	private Member memberEmail;
	
	private String content;
	
	private LocalDateTime createdAt;
}
