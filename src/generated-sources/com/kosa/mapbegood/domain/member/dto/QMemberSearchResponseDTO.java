package com.kosa.mapbegood.domain.member.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kosa.mapbegood.domain.member.dto.QMemberSearchResponseDTO is a Querydsl Projection type for MemberSearchResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberSearchResponseDTO extends ConstructorExpression<MemberSearchResponseDTO> {

    private static final long serialVersionUID = 153197442L;

    public QMemberSearchResponseDTO(com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> profileImage, com.querydsl.core.types.Expression<? extends java.util.List<com.kosa.mapbegood.domain.mymap.thememap.dto.ThemeMapResponseDTO>> themeMapResponseDTOList) {
        super(MemberSearchResponseDTO.class, new Class<?>[]{String.class, String.class, java.util.List.class}, nickname, profileImage, themeMapResponseDTOList);
    }

}

