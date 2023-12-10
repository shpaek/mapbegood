package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto;

import java.time.LocalDateTime;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OurplaceFeedDTO {
    private OurplaceDTO ourplaceId;
    private MemberDTO memberEmail;
    private Long groupId;
    private String content;
    private LocalDateTime createdAt;

}
