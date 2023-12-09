package com.kosa.mapbegood.domain.place.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Place {
    @Id
    private Long id;
    private String placeName;
    private String address;
    private Double x;
    private Double y;
    private String category;
}
