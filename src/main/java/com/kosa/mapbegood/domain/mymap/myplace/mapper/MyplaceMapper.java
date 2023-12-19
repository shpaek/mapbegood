package com.kosa.mapbegood.domain.mymap.myplace.mapper;

import java.util.Objects;
import java.util.Optional;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import org.springframework.security.core.parameters.P;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyplaceMapper {
	default Myplace dtoToEntity(MyplaceDTO myplaceDto) {
		if (Objects.isNull(myplaceDto)) {
			return null;
		}
		Myplace myplace = new Myplace();
		myplace.setThememapId(this.themeMapDtoToEntity(myplaceDto.getThememapId()));
		myplace.setPlaceId(this.placeDtoToEntity(myplaceDto.getPlaceId()));
		myplace.setVisitedAt(myplaceDto.getVisitedAt());
		return myplace;
	};
	default MyplaceDTO entityToDto(Myplace myplace){
		if (Objects.isNull(myplace)) return null;
		MyplaceDTO myplaceDTO = new MyplaceDTO();
		myplaceDTO.setId(myplace.getId());
		myplaceDTO.setThememapId(this.themeMapToDto(myplace.getThememapId()));
		myplaceDTO.setPlaceId(this.placeToDto(myplace.getPlaceId()));
		myplaceDTO.setVisitedAt(myplace.getVisitedAt());
		return myplaceDTO;
	};

	private ThemeMap themeMapDtoToEntity(ThemeMapDto themeMapDto) {
		if (Objects.isNull(themeMapDto)) return null;

		ThemeMap themeMap = new ThemeMap();
		themeMap.setId(themeMapDto.getId());
		return themeMap;
	}

	private Place placeDtoToEntity(PlaceDTO placeDto) {
		if (Objects.isNull(placeDto)) return null;
		Place place = new Place();
		place.setId(placeDto.getId());
		return place;
	}

	private ThemeMapDto themeMapToDto(ThemeMap themeMap) {
		if (Objects.isNull(themeMap)) return null;
		ThemeMapDto themeMapDto = new ThemeMapDto();
		themeMapDto.setId(themeMap.getId());
		themeMapDto.setName(themeMap.getName());
		themeMapDto.setColor(themeMap.getColor());
		themeMapDto.setMemo(themeMap.getMemo());
		themeMapDto.setShow(themeMap.getShow());
		themeMapDto.setMainmap(themeMap.getMainmap());
		return themeMapDto;
	}

	private PlaceDTO placeToDto(Place place) {
		if (Objects.isNull(place)) return null;
		PlaceDTO placeDTO = new PlaceDTO();
		placeDTO.setId(place.getId());
		placeDTO.setPlaceName(place.getPlaceName());
		placeDTO.setAddress(place.getAddress());
		placeDTO.setX(place.getX());
		placeDTO.setY(place.getY());
		placeDTO.setCategory(place.getCategory());
		return placeDTO;
	}
}
