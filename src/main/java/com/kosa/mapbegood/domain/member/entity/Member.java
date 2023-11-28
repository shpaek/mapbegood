package com.kosa.mapbegood.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Member extends AuditEntity {
	
	@Id
	private String nickname;

	private String email;
	
	private String password;
}
