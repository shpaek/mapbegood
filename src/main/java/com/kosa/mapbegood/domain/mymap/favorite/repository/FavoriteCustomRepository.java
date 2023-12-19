package com.kosa.mapbegood.domain.mymap.favorite.repository;

import com.kosa.mapbegood.domain.mymap.favorite.dto.RecommendThemeMapDto;

import java.util.List;

public interface FavoriteCustomRepository {
    List<RecommendThemeMapDto> findReCommendThemeMap(int size);
}
