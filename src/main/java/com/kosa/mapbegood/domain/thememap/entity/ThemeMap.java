package com.kosa.mapbegood.domain.thememap.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table
public class ThemeMap {
	
//	@Id
	private Long id;
	
//	private Member memberNickname;
	
	private String name;
	
	private String color;
	
	private String memo;
	
	private Boolean show;
	
	private Boolean mainmap;
}
