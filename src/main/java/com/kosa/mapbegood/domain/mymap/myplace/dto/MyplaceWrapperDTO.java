package com.kosa.mapbegood.domain.mymap.myplace.dto;

import com.kosa.mapbegood.domain.place.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class MyplaceWrapperDTO {
	private MyplaceDTO myplaceDto;
	private PlaceDTO placeDto;
}
