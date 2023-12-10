package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OurplaceFeedMapper {
    OurplaceFeed dtoToEntity(OurplaceFeedDTO ourfeedDto);

    OurplaceFeedDTO entityToDto(OurplaceFeed ourplaceFeed);
    
    default OurplaceFeedEmbedded mapToOurplaceFeedEmbedded(Long value) {
        OurplaceFeedEmbedded embedded = new OurplaceFeedEmbedded();
        embedded.setOurplaceId(value);
        return embedded;
    }

    default Long mapOurplaceFeedEmbeddedToLong(OurplaceFeedEmbedded value) {
        return value.getOurplaceId();
    }

    default OurplaceFeedEmbedded mapToOurplaceFeedEmbedded(String value) {
        OurplaceFeedEmbedded embedded = new OurplaceFeedEmbedded();
        embedded.setEmail(value);
        return embedded;
    }

    default String mapOurplaceFeedEmbeddedToString(OurplaceFeedEmbedded value) {
        return value.getEmail();
    }
}
