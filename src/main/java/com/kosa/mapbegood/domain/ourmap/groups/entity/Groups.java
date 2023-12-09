package com.kosa.mapbegood.domain.ourmap.groups.entity;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
@SequenceGenerator(name = "groups_seq_gener", sequenceName = "groups_seq", initialValue = 1, allocationSize = 1)
public class Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq_gener")	
	private Long id;
	
	private String name;

	
//	@OneToMany(mappedBy = "", cascade = CascadeType.REMOVE)
//	private List<MemberGroup> memberList;
	
	@OneToMany(mappedBy = "groupId", cascade = CascadeType.REMOVE)
	private List<Waiting> waitingList;
	
	@OneToMany(mappedBy = "groupId", cascade = CascadeType.REMOVE)
	private List<GroupThememap> groupThememapList;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="id.groupsId")
//	@JoinColumn(name=)
	private List<MemberGroup> memberGroupList;

	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "groupId")
	private List<OurplaceFeed> OurplaceFeedList;
	
	public void modifyGroupName(String groupName) {
		this.name = groupName;
	}
}