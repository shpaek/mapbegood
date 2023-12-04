package com.kosa.mapbegood.domain.mymap.thememap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class themeMapDTO {
    private Long id;
    private String name;
    private String color;
    private String memo;
}
