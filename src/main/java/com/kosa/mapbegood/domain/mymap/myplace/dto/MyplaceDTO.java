package com.kosa.mapbegood.domain.mymap.myplace.dto;

import java.sql.Date;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class MyplaceDTO {

	private Long id;

	private ThemeMap thememapId;
	
	private Date visitedAt;
	
	private Long placeId;
	
	private String placeName;
	
	private Double x;
	
	private Double y;
	
	private String category;
	
}
