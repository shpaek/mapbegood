package com.kosa.mapbegood.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PlaceDTO {
	
    private Long id;
    
    private String placeName;
    
    private String address;
    
    private Double x;
    
    private Double y;
    
    private String category;
    
}
