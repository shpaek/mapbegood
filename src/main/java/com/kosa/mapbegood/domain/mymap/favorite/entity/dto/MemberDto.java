package com.kosa.mapbegood.domain.mymap.favorite.entity.dto;

import java.sql.Date;
import java.time.LocalDate;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class MemberDto {
	private String nickname;
	
	private String email;
	
	private String password;
	
	private LocalDate createdAt;
    
}
