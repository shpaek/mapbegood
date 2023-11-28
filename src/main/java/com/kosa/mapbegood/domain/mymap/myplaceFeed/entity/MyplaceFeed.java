package com.kosa.mapbegood.domain.mymap.myplaceFeed.entity;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@JoinColumn(name = "email")
	private Member memberEmail;

	private String content;
}
