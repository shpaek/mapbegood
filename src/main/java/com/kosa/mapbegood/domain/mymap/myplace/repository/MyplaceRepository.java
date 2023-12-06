package com.kosa.mapbegood.domain.mymap.myplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

public interface MyplaceRepository extends JpaRepository<Myplace, Long>  {
	List<Myplace> findBythemeMapId(Long thememapId);
}
