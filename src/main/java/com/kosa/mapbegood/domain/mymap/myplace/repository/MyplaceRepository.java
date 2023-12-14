package com.kosa.mapbegood.domain.mymap.myplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;

public interface MyplaceRepository extends JpaRepository<Myplace, Long>  {
	List<Myplace> findByThememapId_Id(Long thememapId);

}
