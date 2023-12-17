package com.kosa.mapbegood.domain.ourmap.waiting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;
import com.kosa.mapbegood.exception.FindException;

public interface WaitingRepository extends JpaRepository<Waiting, Long>{
	
	public Optional<Waiting> findByGroupIdAndMemberEmail(Groups groupId, String memberEmail) throws FindException;
	public List<Waiting> findByGroupId(Groups groupId) throws FindException;
	public List<Waiting> findByMemberEmail(String memberEmail) throws FindException;
}
