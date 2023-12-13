package com.kosa.mapbegood.domain.mymap.myplaceFeed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private Long myplaceId;

	@ManyToOne
	@JoinColumn(name = "member_email", referencedColumnName = "email")
	private Member memberEmail;

	@Column(name = "content")
	private String content;

    @OneToOne
    @JoinColumn(name = "myplace_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Myplace myplace;

}