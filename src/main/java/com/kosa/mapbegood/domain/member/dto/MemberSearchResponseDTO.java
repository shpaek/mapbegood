package com.kosa.mapbegood.domain.member.dto;

import com.kosa.mapbegood.domain.mymap.thememap.dto.themeMapDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchResponseDTO {
    private String nickName;
    private String profileImage;
    private List<themeMapDTO> themeMapDTOList;
}
