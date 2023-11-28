package com.kosa.mapbegood.domain.ourmap.groups.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
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
	
	private String leaderEmail;
	
//	@OneToMany(mappedBy = "", cascade = CascadeType.REMOVE)
//	private List<MemberGroup> memberList;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "groupId")
	private List<Waiting> waitingList;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "groupId")
	private List<GroupThememap> groupThememapList;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="id.groupsId")
//	@JoinColumn(name=)
	private List<MemberGroup> memberGroupList;
	
	public void modifyGroupName(String groupName) {
		this.name = groupName;
	}
}