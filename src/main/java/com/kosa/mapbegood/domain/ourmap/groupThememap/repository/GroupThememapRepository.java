package com.kosa.mapbegood.domain.ourmap.groupThememap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

public interface GroupThememapRepository extends JpaRepository<GroupThememap,Long> {

	List<GroupThememap> findByGroupId(Groups groupId);
//
	Optional<GroupThememap> findByIdAndGroupId(Long groupThememapId, Long id);

}
