package com.kosa.mapbegood.domain.ourmap.ourplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;

public interface OurplaceRepository extends JpaRepository<Ourplace, Long>  {
	List<Ourplace> findByGroupThememapId_Id(GroupThememap groupThememapId);
}
