package com.kosa.mapbegood.domain.mymap.myplace.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyplaceMapper {
	Myplace dtoToEntity(MyplaceDTO myplaceDto);	
	MyplaceDTO entityToDto(Myplace myplace);
	
}
