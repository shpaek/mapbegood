package com.kosa.mapbegood.domain.member.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Member {
	
	@Id
	private String nickname;
	
	private String email;
	
	private String passwrod;
	
	private Date createdAt;
}
