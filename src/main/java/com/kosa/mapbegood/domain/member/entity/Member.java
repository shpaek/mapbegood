package com.kosa.mapbegood.domain.member.entity;

import com.kosa.mapbegood.domain.common.entity.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String nickname;

	private String email;

	private String password;
}
