package com.kosa.mapbegood.domain.mymap.thememap.entity.dto;

import java.util.List;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data 
public class ThemeMapdto{
	private Long id;
	private Member memberNickname;
	private String name;
	private String color;
	private String memo;
	private Boolean show;
	private Boolean mainmap;
	private List<Myplace> myplaceList;
}
