package com.kosa.mapbegood.domain.ourmap.ourplace.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class OurplaceDTO {
	private Long id;
	private Long groupThememapId;
	private String memberEmail;
	private Long placeId;
	private String placeName;
	private Double x;
	private Double y;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date visitedAt;
	private String category;
}
