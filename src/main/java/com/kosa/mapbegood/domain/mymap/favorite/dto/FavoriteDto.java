package com.kosa.mapbegood.domain.mymap.favorite.dto;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
	
public class FavoriteDto {
    
	private ThemeMapDto themeMapDto;
    private ThemeMap thememapId;
    private Member Memberemail;
//    private date createAt;
    public ThemeMapDto getThemeMapDto() {
        return themeMapDto;
    }

    public void setThemeMapDto(ThemeMapDto themeMapDto) {
        this.themeMapDto = themeMapDto;
    }
}