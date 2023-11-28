package com.kosa.mapbegood.domain.mymap.favorite.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FavoriteEmbedded implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "thememap_id")
	private Long thememapId;
	
	@Column(name = "member_email")
	private String email;
}
