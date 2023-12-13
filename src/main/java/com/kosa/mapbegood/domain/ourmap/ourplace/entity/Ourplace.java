package com.kosa.mapbegood.domain.ourmap.ourplace.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.place.entity.Place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="id.ourplaceId")
	private List<OurplaceFeed> feedList;

//	@OneToMany(cascade = CascadeType.REMOVE)
//	private List<Place> placeList;
}
