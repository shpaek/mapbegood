package com.kosa.mapbegood.domain.ourmap.memberGroup.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

import lombok.Data;

@Data
@Entity
@Table
public class MemberGroup {

	@EmbeddedId
	private MemberGroupEmbedded id = new MemberGroupEmbedded();
	
	@ManyToOne
	@JoinColumn(name = "member_email")
	@MapsId("email")
	private Member memberEmail;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	@MapsId("groupsId")
	private Groups groupId;
	
	private int leader;
}
