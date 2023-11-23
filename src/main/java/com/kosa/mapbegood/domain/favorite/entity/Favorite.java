package com.kosa.mapbegood.domain.favorite.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.thememap.entity.ThemeMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table
public class Favorite extends AuditEntity {
	
	@EmbeddedId
	private FavoriteEmbedded id = new FavoriteEmbedded();
	
	@ManyToOne
	@JoinColumn(name = "ThemeMapId")
	@MapsId("thememapId")
	private ThemeMap thememapId;
	
	@ManyToOne
	@JoinColumn(name = "nickname")
	@MapsId("nickname")
	private Member memberNickname;
}
