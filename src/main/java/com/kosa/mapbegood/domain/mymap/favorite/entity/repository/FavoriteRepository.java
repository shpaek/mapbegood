package com.kosa.mapbegood.domain.mymap.favorite.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteEmbedded>{
	// 사용자의 즐겨찾기 리스트를 조회하는 메서드
    @Query("SELECT f FROM Favorite f WHERE f.id.nickname = :nickname")
    List<Favorite> findFavoritesByNickname(String nickname);
    
    
	List<Favorite> findByMemberNickname_Nickname(String nickname);
}
