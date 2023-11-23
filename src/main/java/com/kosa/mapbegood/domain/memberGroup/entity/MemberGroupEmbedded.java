package com.kosa.mapbegood.domain.memberGroup.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.kosa.mapbegood.domain.favorite.entity.FavoriteEmbedded;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Embeddable
public class MemberGroupEmbedded implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "member_nickname")
	private String nickname;
	
	@Column(name = "groups_id")
	private Long groupsId;
}
