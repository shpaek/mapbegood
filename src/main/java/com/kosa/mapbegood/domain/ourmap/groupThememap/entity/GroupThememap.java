package com.kosa.mapbegood.domain.ourmap.groupThememap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;

import lombok.Data;

@Data
@Entity
@Table
public class GroupThememap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private Long groupId;
	
	private String name;
	
	private String color;
	
	private String memo;
	
	@OneToMany(mappedBy = "groupThememapId", cascade = CascadeType.REMOVE)
	private List<Ourplace> ourplaceList;
}
