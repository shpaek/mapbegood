package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OurplaceFeedDTO {

    private Long ourplaceId;
    private String memberNickname;
    private String content;
    private LocalDateTime createdAt;

    public OurplaceFeedDTO(Long ourplaceId, String memberNickname) {
        this.ourplaceId = ourplaceId;
        this.memberNickname = memberNickname;
    }
}
