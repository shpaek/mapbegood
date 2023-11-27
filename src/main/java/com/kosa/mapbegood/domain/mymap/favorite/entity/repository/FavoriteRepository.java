package com.kosa.mapbegood.domain.mymap.favorite.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteEmbedded>{

}
