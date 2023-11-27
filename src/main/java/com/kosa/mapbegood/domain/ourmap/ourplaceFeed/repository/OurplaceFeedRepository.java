package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.repository;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OurplaceFeedRepository extends JpaRepository<OurplaceFeed, OurplaceFeedEmbedded>
{
}
