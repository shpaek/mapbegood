package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;
import lombok.*;

import javax.persistence.*;

//@Setter
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
    @MapsId("ourplaceId")
//    @JoinColumn(name = "ourplace_id")
    private Ourplace ourplace;

    @ManyToOne
    @MapsId("email")
//    @JoinColumn(name = "member_email")
    private Member member;

	private Long groupId;
	
	private String content;
}
