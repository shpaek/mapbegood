package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
