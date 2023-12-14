package com.kosa.mapbegood.domain.mymap.thememap.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kosa.mapbegood.domain.mymap.thememap.dto.QThemeMapResponseDTO is a Querydsl Projection type for ThemeMapResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QThemeMapResponseDTO extends ConstructorExpression<ThemeMapResponseDTO> {

    private static final long serialVersionUID = -73290168L;

    public QThemeMapResponseDTO(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> color, com.querydsl.core.types.Expression<String> memo) {
        super(ThemeMapResponseDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class}, id, name, color, memo);
    }

}

