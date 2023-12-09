package com.kosa.mapbegood.domain.ourmap.ourplace.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.member.entity.Member;

import com.kosa.mapbegood.domain.place.entity.Place;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table
@SequenceGenerator(name = "ourplace_seq_gener", sequenceName = "ourplace_seq", initialValue = 1, allocationSize = 1)
public class Ourplace extends AuditEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ourplace_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "GroupThememapId")
	private GroupThememap groupThememapId;
	
	@ManyToOne
	@JoinColumn(name = "member_email")
	private Member memberEmail;

	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place placeId;

	private Date visitedAt;

//	private String placeName;
	
//	private Double x;
	
//	private Double y;

//	private String category;
	
//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<OurplaceFeed> feedList;

//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<Place> placeList;
}
