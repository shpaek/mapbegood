package com.kosa.mapbegood.domain.ourplaceFeed.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourplace.entity.Ourplace;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table
public class OurplaceFeed extends AuditEntity {
	
	@EmbeddedId
	private OurplaceFeedEmbedded id = new OurplaceFeedEmbedded();
	
	@ManyToOne
	@JoinColumn(name = "OurplaceId")
	@MapsId("ourplaceId")
	private Ourplace ourplaceId;
	
	@OneToOne
	@JoinColumn(name = "nickname")
	@MapsId("nickname")
	private Member memberNickname;
	
	private String content;
}
