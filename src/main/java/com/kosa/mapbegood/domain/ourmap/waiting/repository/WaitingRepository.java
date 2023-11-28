package com.kosa.mapbegood.domain.ourmap.waiting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;

public interface WaitingRepository extends JpaRepository<Waiting, Long>{

}
