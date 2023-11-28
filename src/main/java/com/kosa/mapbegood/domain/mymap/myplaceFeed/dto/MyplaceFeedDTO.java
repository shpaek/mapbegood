package com.kosa.mapbegood.domain.mymap.myplaceFeed.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyplaceFeedDTO {

	private Long myplaceId;
	
	private String memberEmail;
	
	private String content;
	
	private LocalDateTime createdAt;
}
