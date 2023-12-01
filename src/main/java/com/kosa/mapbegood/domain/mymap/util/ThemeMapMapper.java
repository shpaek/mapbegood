package com.kosa.mapbegood.domain.mymap.util;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.dto.MemberDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

public class ThemeMapMapper {
	public static ThemeMap toEntity(ThemeMapDto dto) {
		ThemeMap entity = new ThemeMap();
		//TODO
		entity.setId(dto.getId());
		Member member = new Member();
        member.setEmail(dto.getMemberEmail().getEmail());  // 변경된 부분
        entity.setMemberEmail(member);		entity.setMainmap(dto.getMainmap());
//		entity.setFavoriteList(dto.getMyplaceList());
		entity.setShow(dto.getShow());
		entity.setName(dto.getName());
		entity.setMemo(dto.getMemo());
		entity.setColor(dto.getColor());
		return entity;
	}
	
	public static ThemeMapDto toDto(ThemeMap entity) {
		ThemeMapDto dto = new ThemeMapDto();
		//TODO
	        // 필요한 경우, 다양한 필드들을 ThemeMap 엔터티에서 가져와 ThemeMapDto에 설정합니다.
	        dto.setId(entity.getId());
//	        MemberDto memberDto = new MemberDto();
//	        memberDto.setEmail(entity.getMemberEmail().getEmail());  // 변경된 부분
	        dto.setMemberEmail(entity.getMemberEmail());	       
	        dto.setMainmap(entity.getMainmap());
	        dto.setShow(entity.getShow());
	        dto.setName(entity.getName());
	        dto.setMemo(entity.getMemo());
	        dto.setColor(entity.getColor());
	        // MyplaceList 등 필요한 필드들을 설정합니다.
	        // dto.setMyplaceList(entity.getFavoriteList());
	        return dto;
	  
	}
}
