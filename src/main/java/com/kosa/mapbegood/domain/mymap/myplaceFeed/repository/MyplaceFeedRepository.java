package com.kosa.mapbegood.domain.mymap.myplaceFeed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;

public interface MyplaceFeedRepository extends JpaRepository<MyplaceFeed, Long> {
//	List<MyplaceFeed> findByMyplace_Thememap_Show(Boolean show);
}
