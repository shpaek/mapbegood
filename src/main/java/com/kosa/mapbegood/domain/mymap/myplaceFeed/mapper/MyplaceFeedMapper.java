package com.kosa.mapbegood.domain.mymap.myplaceFeed.mapper;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyplaceFeedMapper {
    MyplaceFeed MyplaceFeedDTOtoMyplaceFeed(MyplaceFeedDTO myfeedDto);

}
