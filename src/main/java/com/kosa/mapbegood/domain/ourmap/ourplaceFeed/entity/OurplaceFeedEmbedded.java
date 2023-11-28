package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OurplaceFeedEmbedded implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ourplace_id")
	private Long ourplaceId;
	
	@Column(name = "member_email")
	private String email;
}
