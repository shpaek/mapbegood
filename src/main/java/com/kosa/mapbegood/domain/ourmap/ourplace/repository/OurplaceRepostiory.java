package com.kosa.mapbegood.domain.ourmap.ourplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

public interface OurplaceRepostiory extends JpaRepository<Myplace, Long>  {

}
