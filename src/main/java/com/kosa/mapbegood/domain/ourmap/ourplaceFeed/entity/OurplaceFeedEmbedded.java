package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Embeddable
public class OurplaceFeedEmbedded implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ourplace_id")
	private Long ourplaceId;
	
	@Column(name = "member_nickname")
	private String nickname;
}
