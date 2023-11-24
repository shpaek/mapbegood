package com.kosa.mapbegood.domain.ourmap.ourplace.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.member.entity.Member;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table
@SequenceGenerator(name = "ourplace_seq_gener", sequenceName = "ourplace_seq", initialValue = 1, allocationSize = 1)
public class Ourplace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ourplace_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "GroupThememapId")
	private GroupThememap groupThememapId;
	
	@ManyToOne
	@JoinColumn(name = "nickname")
	private Member memberNickname;
	
	private Long placeId;
	
	private String placeName;
	
	private Double x;
	
	private Double y;
		
	private Date visitedAt;
	
	private String category;
	
//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<OurplaceFeed> feedList;
}
