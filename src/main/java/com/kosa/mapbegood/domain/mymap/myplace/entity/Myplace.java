package com.kosa.mapbegood.domain.mymap.myplace.entity;

<<<<<<< HEAD
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
=======
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import com.kosa.mapbegood.domain.place.entity.Place;
>>>>>>> 2d312b8be56a47f48605df689b35bf026560ca5b
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
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
	
//	@OneToOne(cascade = CascadeType.REMOVE)
//	private MyplaceFeed feed;

//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<Place> placeList;

}
