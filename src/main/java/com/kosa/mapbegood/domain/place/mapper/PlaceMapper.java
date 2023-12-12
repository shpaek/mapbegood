package com.kosa.mapbegood.domain.place.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.entity.Place;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceMapper {
	Place dtoToEntity(PlaceDTO placeDto);
	PlaceDTO entityToDto(Place place);
}

