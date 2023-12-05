package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.mapper;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OurplaceFeedMapper {
    OurplaceFeed OurplaceFeedDTOtoOurplaceFeed(OurplaceFeedDTO ourfeedDto);

    OurplaceFeedEmbedded ourplaceFeedDTOtoOurplaceFeedEmbedded(OurplaceFeedDTO ourfeedDto);

    OurplaceFeedDTO OurplaceFeedToOurplaceFeedDTO(OurplaceFeed ourplaceFeed);
}
