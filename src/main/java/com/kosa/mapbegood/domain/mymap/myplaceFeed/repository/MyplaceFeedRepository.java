package com.kosa.mapbegood.domain.mymap.myplaceFeed.repository;

import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyplaceFeedRepository extends JpaRepository<MyplaceFeed, Long> {

}
