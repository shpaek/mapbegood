package com.kosa.mapbegood.domain.mymap.myplace.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.place.entity.Place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SequenceGenerator(name = "myplace_seq_gener", sequenceName = "myplace_seq", initialValue = 1, allocationSize = 1)
public class Myplace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myplace_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "thememap_id")
	private ThemeMap thememapId;

	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place placeId;

	private Date visitedAt;

//	private String placeName;
	
//	private Double x;
	
//	private Double y;
	
//	private String category;
	
//	@OneToOne(mappedBy = "myplace", cascade = CascadeType.REMOVE)
//	private MyplaceFeed feed;

//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<Place> placeList;
}
