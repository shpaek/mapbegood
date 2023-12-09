package com.kosa.mapbegood.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
