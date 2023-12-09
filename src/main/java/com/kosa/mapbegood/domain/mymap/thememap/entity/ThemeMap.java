package com.kosa.mapbegood.domain.mymap.thememap.entity;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "thememap")
@SequenceGenerator(name = "thememap_seq_gener", sequenceName = "thememap_seq", initialValue = 1, allocationSize = 1)
public class ThemeMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thememap_seq_gener")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "Member_email")
	private Member memberEmail;
	
	private String name;
	
	private String color;
	
	private String memo;
	
	private Boolean show;
	
	private Boolean mainmap;
	
	@OneToMany(mappedBy = "thememapId", cascade = CascadeType.REMOVE)
	private List<Myplace> myplaceList;
	
	@OneToMany(mappedBy = "thememapId", cascade = CascadeType.REMOVE)
	private List<Favorite> favoriteList;
}
