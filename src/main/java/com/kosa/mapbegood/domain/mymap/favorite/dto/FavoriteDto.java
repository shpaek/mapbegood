package com.kosa.mapbegood.domain.mymap.favorite.dto;

import java.util.Date;

import com.kosa.mapbegood.domain.mymap.favorite.dto.MemberDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FavoriteDto {

    private Long thememapId;
    private String memberEmail;
    private Long createAt;
    
}