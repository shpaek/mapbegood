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
	@JoinColumn(name = "myplaceId")
	private Long myplaceId;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member memberEmail;

	private String content;

	@OneToOne
	@JoinColumn(name = "myplaceId", referencedColumnName = "id", insertable = false, updatable = false)
	private Myplace myplace;
}
