package com.kosa.mapbegood.domain.ourmap.waiting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "waiting_seq_gener", sequenceName = "waiting_seq", initialValue = 1, allocationSize = 1)
public class Waiting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="waiting_seq_gener")
	private Long id;
	
	private Long groupId;
	
	@Column(name="member_email")
	private String memberEmail;
	
//	@ManyToOne
//	@JoinColumn(name="member_email", insertable = false, updatable = false)
//	private Member membeEmailr;
}
