package com.kosa.mapbegood.domain.ourmap.groups.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;

import lombok.Data;

@Data
@Entity
@Table
@SequenceGenerator(name = "groups_seq_gener", sequenceName = "groups_seq", initialValue = 1, allocationSize = 1)
public class Groups {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq_gener")	
	private Long id;
	
	private String name;
	
	private String leaderNickname;
	
//	@OneToMany(mappedBy = "", cascade = CascadeType.REMOVE)
//	private List<MemberGroup> memberList;
	
	@OneToMany(mappedBy = "groupId", cascade = CascadeType.REMOVE)
	private List<Waiting> waitingList;
	
	@OneToMany(mappedBy = "groupId", cascade = CascadeType.REMOVE)
	private List<GroupThememap> groupThememapList;
}