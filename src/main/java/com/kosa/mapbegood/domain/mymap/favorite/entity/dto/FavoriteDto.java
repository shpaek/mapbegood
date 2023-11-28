package com.kosa.mapbegood.domain.mymap.favorite.entity.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FavoriteDto {

    private ThemeMapDto themeMap; //테마지도아이디
    private MemberDto member; //작성자
    private Date createat;//작성시간

   
    
}