package com.kosa.mapbegood.domain.mymap.favorite.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.print.DocFlavor;

@Getter
@NoArgsConstructor
@ToString
public class RecommendThemeMapDto {
    private Long cnt;
    private Long themeMapId;
    private String themeMapName;
    private String themeMapMemo;
    private String memberNickName;

    @QueryProjection
    public RecommendThemeMapDto(Long cnt, Long themeMapId, String themeMapName, String themeMapMemo, String memberNickName) {
        this.cnt = cnt;
        this.themeMapId = themeMapId;
        this.themeMapName = themeMapName;
        this.themeMapMemo = themeMapMemo;
        this.memberNickName = memberNickName;
    }
}
