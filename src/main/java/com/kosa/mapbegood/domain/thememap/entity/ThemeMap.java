package com.kosa.mapbegood.domain.thememap.entity;

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

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.myplace.entity.Myplace;

import lombok.Data;

@Data
@Entity
@Table(name = "thememap")
@SequenceGenerator(name = "thememap_seq_gener", sequenceName = "thememap_seq", initialValue = 1, allocationSize = 1)
public class ThemeMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thememap_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "nickname")
	private Member memberNickname;
	
	private String name;
	
	private String color;
	
	private String memo;
	
	private Boolean show;
	
	private Boolean mainmap;
	
	@OneToMany(mappedBy = "thememapId", cascade = CascadeType.REMOVE)
	private List<Myplace> myplaceList;
}
