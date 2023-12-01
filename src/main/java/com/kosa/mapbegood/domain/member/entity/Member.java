package com.kosa.mapbegood.domain.member.entity;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Member extends AuditEntity {
	@Id
	private String email;

	private String nickname;
	
	private String password;

	private int status;
}
