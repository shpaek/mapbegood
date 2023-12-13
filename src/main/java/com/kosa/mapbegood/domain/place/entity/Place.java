package com.kosa.mapbegood.domain.place.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

import javax.persistence.*;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
