package com.kosa.mapbegood.domain.ourmap.waiting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Waiting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private Long groupId;
	
	@Column(name="member_email")
	private String memberEmail;
	
//	@ManyToOne
//	@JoinColumn(name="member_email", insertable = false, updatable = false)
//	private Member membeEmailr;
}
