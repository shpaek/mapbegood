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
	@Column(name = "myplace_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long myplaceId;

	@ManyToOne
	@JoinColumn(name = "member_email", referencedColumnName = "email")
	private Member memberEmail;

	@Column(name = "content")
	private String content;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "myplace_id", referencedColumnName = "id", unique = true)
	private Myplace myplace;

}