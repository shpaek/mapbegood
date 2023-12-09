package com.kosa.mapbegood.domain.mymap.myplace.dto;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.place.entity.Place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class MyplaceDTO {

	private Long id;
	
	private ThemeMap thememapId;

	private Place placeId;

	private Date visitedAt;
	
}
