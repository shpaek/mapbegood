package com.kosa.mapbegood.domain.mymap.favorite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteEmbedded>, FavoriteCustomRepository {

	List<Favorite> findByMemberEmail(Member member);

	Optional<Favorite> findByMemberEmailAndThememapId(Member member, Long themeMapId);
    
//	List<Favorite> findFavoriteByEmail(String Email);
//	// 사용자의 즐겨찾기 리스트를 조회하는 메서드
//	@Query("SELECT f FROM Favorite f WHERE f.id.nickname = :nickname")
//    List<Favorite> findFavoritesByNickname(String nickname);
    
   
}
