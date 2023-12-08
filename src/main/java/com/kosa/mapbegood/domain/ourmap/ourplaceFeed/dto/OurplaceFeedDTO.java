package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OurplaceFeedDTO {
    private Long ourplaceId;
    private String memberEmail;
    private Long groupId;
    private String content;
    private LocalDateTime createdAt;

}
