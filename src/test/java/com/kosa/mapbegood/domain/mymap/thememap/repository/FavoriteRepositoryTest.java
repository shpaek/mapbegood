package com.kosa.mapbegood.domain.mymap.thememap.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;
import com.kosa.mapbegood.domain.mymap.favorite.entity.repository.FavoriteRepository;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;

@SpringBootTest
public class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ThemeMapRepository themeMapRepository;

    @Test
    public void insertFavorite() {
        Favorite favorite = new Favorite();
        FavoriteEmbedded favoriteem = new FavoriteEmbedded();
        favoriteem.setThememapId(1L); 
        favoriteem.setNickname("test");
        favorite.setId(favoriteem);

        Optional<ThemeMap> themeMapOptional = themeMapRepository.findById(1L);
        if (themeMapOptional.isPresent()) {
            ThemeMap themeMap = themeMapOptional.get();
            favoriteem.setThememapId(themeMap.getId());
            favoriteem.setNickname(themeMap.getMemberNickname().getNickname());
            favorite.setId(favoriteem);
            favorite.setThememapId(themeMap);
            favorite.setMemberNickname(themeMap.getMemberNickname());
        }

        favoriteRepository.save(favorite);
    }
	
    @Test
	private void selectFavorite(){
		
		
	}
	
}
