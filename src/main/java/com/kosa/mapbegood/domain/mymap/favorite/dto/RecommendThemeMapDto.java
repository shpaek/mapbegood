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
    private Long id;
    private String name;
    private String memo;
    private String nickname;

    @QueryProjection
    public RecommendThemeMapDto(Long cnt, Long id, String name, String memo, String nickname) {
        this.cnt = cnt;
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.nickname = nickname;
    }
}
