package com.kosa.mapbegood.domain.mymap.myplace.entity;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
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
	
	private Date visitedAt;
	
	private Long placeId;
	
	private String placeName;
	
	private Double x;
	
	private Double y;
	
	private String category;

	@OneToOne(mappedBy = "myplace", cascade = CascadeType.ALL)
	@JoinColumn(name = "myplace_id")
	private MyplaceFeed myplaceFeed;
}
