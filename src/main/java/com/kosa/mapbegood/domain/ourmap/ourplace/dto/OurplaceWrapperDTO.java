package com.kosa.mapbegood.domain.ourmap.ourplace.dto;

import com.kosa.mapbegood.domain.place.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class OurplaceWrapperDTO {
	private OurplaceDTO ourplaceDto;
	private PlaceDTO placeDto;
}
