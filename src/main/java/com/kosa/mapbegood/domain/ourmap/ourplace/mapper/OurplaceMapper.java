package com.kosa.mapbegood.domain.ourmap.ourplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)


public interface OurplaceMapper {
    Ourplace dtoToEntity(OurplaceDTO ourplaceDto);

    OurplaceDTO entityToDto(Ourplace ourplace);

}
