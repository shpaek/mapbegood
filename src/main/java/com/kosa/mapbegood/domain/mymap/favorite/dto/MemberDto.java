package com.kosa.mapbegood.domain.mymap.favorite.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter @NoArgsConstructor @AllArgsConstructor
public class MemberDto {
	private String nickname;
	
	private String memberemail;
	
	private String password;
	
	private int status;
    
	
}
