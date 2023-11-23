package com.kosa.mapbegood.domain.memberGroup.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.favorite.entity.FavoriteEmbedded;
import com.kosa.mapbegood.domain.groups.entity.Groups;
import com.kosa.mapbegood.domain.member.entity.Member;

import lombok.Data;

@Data
@Entity
@Table
public class MemberGroup {

	@EmbeddedId
	private MemberGroupEmbedded id = new MemberGroupEmbedded();
	
	@ManyToOne
	@JoinColumn(name = "nickname")
	@MapsId("nickname")
	private Member memberNickname;
	
	@ManyToOne
	@JoinColumn(name = "groupsId")
	@MapsId("groupsId")
	private Groups groupId;
	
	private Boolean leader;
}
