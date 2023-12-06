package com.kosa.mapbegood.domain.mymap.myplace.mapper;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

public class MyplaceMapper {

	public static Myplace toEntity(MyplaceDTO dto) {
		
		Myplace entity =new Myplace();
		
		entity.setId(dto.getId());
		
		ThemeMap thememap=new ThemeMap();
	
		
		return null;
}
	
	public static Myplace toDto(Myplace entity) {
		
		
		
		
		return null;
	
	}
}