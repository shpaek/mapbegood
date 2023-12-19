package com.kosa.mapbegood.domain.place.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.entity.Place;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceMapper {
	default Place dtoToEntity(PlaceDTO placeDto) {
		if (Objects.isNull(placeDto)) {
			return null;
		}
		Place place = new Place();
		place.setId(placeDto.getId());
		place.setPlaceName(placeDto.getPlaceName());
		place.setAddress(placeDto.getAddress());
		place.setX(placeDto.getX());
		place.setY(placeDto.getY());
		place.setCategory(placeDto.getCategory());
		return place;
	}
	default PlaceDTO entityToDto(Place place) {
		if (Objects.isNull(place)) {
			return null;
		}
		PlaceDTO placeDTO = new PlaceDTO();
		placeDTO.setId(place.getId());
		placeDTO.setPlaceName(place.getPlaceName());
		placeDTO.setAddress(place.getAddress());
		placeDTO.setX(place.getX());
		placeDTO.setY(place.getY());
		placeDTO.setCategory(place.getCategory());
		return placeDTO;
	};
}

