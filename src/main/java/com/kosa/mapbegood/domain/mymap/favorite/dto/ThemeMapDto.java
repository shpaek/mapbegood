package com.kosa.mapbegood.domain.mymap.favorite.dto;

import java.util.List;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeMapDto {
    private Long id;
//    private Member MemberEmail;
    private String name;
    private String color;
    private String memo;
    private Boolean show;
    private Boolean mainmap;
}
//    private List<MyPlaceDto> myplaceList;
//    private List<Favorite> favoriteList;
    // Constructors, getters, and setters

//    // Static method to convert ThemeMap to ThemeMapDto
//    public static ThemeMapDto convertToDto(ThemeMap themeMap) {
//        ThemeMapDto themeMapDto = new ThemeMapDto();
//        themeMapDto.setId(themeMap.getId());
//        themeMapDto.setMemberNickname(themeMap.getMemberNickname());
//        themeMapDto.setName(themeMap.getName());
//        themeMapDto.setColor(themeMap.getColor());
//        themeMapDto.setMemo(themeMap.getMemo());
//        themeMapDto.setShow(themeMap.getShow());
//        themeMapDto.setMainmap(themeMap.getMainmap());
//        // Set myplaceList if needed
//
//        return themeMapDto;
//    }
//
//    
//    public static ThemeMap convertToEntity(ThemeMapDto themeMapDto) {
//        ThemeMap themeMap = new ThemeMap();
//        themeMap.setMemberNickname(themeMapDto.getMemberNickname());
//        themeMap.setName(themeMapDto.getName());
//        themeMap.setColor(themeMapDto.getColor());
//        themeMap.setMemo(themeMapDto.getMemo());
//        themeMap.setShow(themeMapDto.getShow());
//        themeMap.setMainmap(themeMapDto.getMainmap());
//        // Set myplaceList if needed
//
//        return themeMap;
    
