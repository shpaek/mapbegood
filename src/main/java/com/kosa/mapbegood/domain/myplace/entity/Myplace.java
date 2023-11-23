package com.kosa.mapbegood.domain.myplace.entity;

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

import com.kosa.mapbegood.domain.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.thememap.entity.ThemeMap;

import lombok.Data;

@Data
@Entity
@Table
@SequenceGenerator(name = "myplace_seq_gener", sequenceName = "myplace_seq", initialValue = 1, allocationSize = 1)
public class Myplace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myplace_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ThemeMapId")
	private ThemeMap thememapId;
	
	private Date visitedAt;
	
	private Long palceId;
	
	private String placeName;
	
	private Double x;
	
	private Double y;
	
	private String category;
	
//	@OneToOne(cascade = CascadeType.REMOVE)
//	private MyplaceFeed feed;
}
