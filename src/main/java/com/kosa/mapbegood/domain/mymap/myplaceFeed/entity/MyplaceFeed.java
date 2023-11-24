package com.kosa.mapbegood.domain.mymap.myplaceFeed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table
public class MyplaceFeed extends AuditEntity {
	
	@Id
	@Column(name = "myplaceId")
	private Long id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "myplaceId")
	private Myplace myplaceId;
	
	@OneToOne
	@JoinColumn(name = "nickname")
	private Member memberNickname;

	private String content;
}
