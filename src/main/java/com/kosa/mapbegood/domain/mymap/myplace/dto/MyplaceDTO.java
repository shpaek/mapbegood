package com.kosa.mapbegood.domain.mymap.myplace.dto;

import java.sql.Date;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class MyplaceDTO {

	private Long id;
	
	private ThemeMapDto thememapId;

	private PlaceDTO placeId;

	private Date visitedAt;
	
}
