package com.kosa.mapbegood.domain.mymap.thememap.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThemeMapResponseDTO {
    private Long id;
    private String name;
    private String color;
    private String memo;

    @QueryProjection
    public ThemeMapResponseDTO(Long id, String name, String color, String memo) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.memo = memo;
    }
}
