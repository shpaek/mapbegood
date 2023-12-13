package com.kosa.mapbegood.domain.member.dto;

import com.kosa.mapbegood.domain.mymap.thememap.dto.ThemeMapResponseDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberSearchResponseDTO {
    private String email;
    private String nickname;
    private String profileImage;
    private List<ThemeMapResponseDTO> themeMapResponseDTOList;

    @QueryProjection
    public MemberSearchResponseDTO(String email, String nickname, String profileImage, List<ThemeMapResponseDTO> themeMapResponseDTOList) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.themeMapResponseDTOList = themeMapResponseDTOList;
    }
}
