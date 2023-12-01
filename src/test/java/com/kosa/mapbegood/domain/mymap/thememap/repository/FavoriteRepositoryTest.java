package com.kosa.mapbegood.domain.mymap.thememap.repository;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;
import com.kosa.mapbegood.domain.mymap.favorite.repository.FavoriteRepository;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ThemeMapRepository themeMapRepository;

    @Test
    public void insertFavorite() {
        Favorite favorite = new Favorite();
        FavoriteEmbedded favoriteem = new FavoriteEmbedded();
        favoriteem.setThememapId(3L); 
        favoriteem.setEmail("test@mail.com");
        favorite.setId(favoriteem);

        Optional<ThemeMap> themeMapOptional = themeMapRepository.findById(3L);
        if (themeMapOptional.isPresent()) {
            ThemeMap themeMap = themeMapOptional.get();
            favoriteem.setThememapId(themeMap.getId());
            favoriteem.setEmail("test@mail.com");
            favorite.setId(favoriteem);
            favorite.setThememapId(themeMap);
            favorite.setMemberEmail(null);
        }

        favoriteRepository.save(favorite);
    }

//    @Test
//	public void selectFavorite(){
//    	log.error("selectFavorite--1");
// 		FavoriteEmbedded favoriteem =new FavoriteEmbedded();
//		favoriteem.setThememapId(1L);
//		favoriteem.setNickname("test");
//		
//		Optional<Favorite> favoriteOptional= 
//				favoriteRepository.findById(favoriteem);
//		
//		//존재할시에 
//		if(favoriteOptional.isPresent()) {
//			Favorite favorite = favoriteOptional.get();
//	        ThemeMap themeMap = favorite.getThememapId();
//	        
//			System.out.println("조회되었습니다"+themeMap.getName());	
//		}else {
//			System.out.println("---실패--");
//		}
//		
//		
//	}
    @Test
    public void selectFavoriteList() {
        
//        String nickname = "test";
//
//        // 즐겨찾기한 리스트 조회
//        List<Favorite> favoriteList = favoriteRepository.findByMemberNickname_Nickname(nickname);
//
//        // 즐겨찾기한 테마맵 리스트 출력
//        for (Favorite favorite : favoriteList) {
//            ThemeMap themeMap = favorite.getThememapId();
//            System.out.println("조회되었습니다: " + themeMap.getName());
//        }
//
//        if (favoriteList.isEmpty()) {
//            System.out.println("---실패--");
//        }
    }
	
}
