package com.kosa.mapbegood.domain.ourmap.groupThememap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SequenceGenerator(
		   name = 
		       "group_thememap_seq_gener", // 사용할 sequence 이름
		   sequenceName =
		         "GROUP_THEMEMAP_SEQ", // 실제 데이터베이스 sequence 이름
		   initialValue = 1, allocationSize = 1
		)
public class GroupThememap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_thememap_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Groups groupId;
	
	private String name;
	
	private String color;
	
	private String memo;
	
	@OneToMany(mappedBy = "groupThememapId", cascade = CascadeType.REMOVE)
	private List<Ourplace> ourplaceList;
}