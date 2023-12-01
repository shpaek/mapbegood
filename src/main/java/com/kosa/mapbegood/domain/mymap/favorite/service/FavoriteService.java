package com.kosa.mapbegood.domain.mymap.favorite.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.member.service.MemberService;
import com.kosa.mapbegood.domain.mymap.favorite.dto.FavoriteDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.MemberDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.MyPlaceDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;
import com.kosa.mapbegood.domain.mymap.favorite.repository.FavoriteRepository;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.repository.ThemeMapRepository;
import com.kosa.mapbegood.domain.mymap.util.ThemeMapMapper;
import com.kosa.mapbegood.exception.FindException;

import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ThemeMapRepository themeMapRepository;	
    private final MemberRepository memberrepository;
//    private final ThemeMapMapper thememapmapper;
    @Autowired
    public FavoriteService(
            FavoriteRepository favoriteRepository,
            ThemeMapRepository themeMapRepository,
            MemberRepository memberrepository
    		) {
        this.favoriteRepository = favoriteRepository;
        this.themeMapRepository = themeMapRepository;
        this.memberrepository=memberrepository;
//        this.thememapmapper=thememapmapper;
    }
    
    public void createFavorite(FavoriteDto favoriteDto) {
        Favorite favorite = new Favorite();

        Member memberDto = favoriteDto.getMemberemail();
        try {
			Member member = memberrepository.findByEmail(memberDto.getEmail())
			        .orElseThrow(() -> new FindException("멤버가 없습니다."));
		    ThemeMap tm = themeMapRepository.findById(favoriteDto.getThememapId().getId())
	                .orElseThrow(() -> new FindException("테마지도가 없습니다."));

		    favorite.setThememapId(tm);
		    favorite.setMemberEmail(member);		    
        } catch (FindException e) {
 			
        	e.printStackTrace();
		}
//        member.setEmail(memberDto);
        favoriteRepository.save(favorite);
    }
}


 
//    public List<FavoriteDto> selectFavoriteList(String Email) {
//    	  
//    	List<Favorite> list = favoriteRepository.findFavoriteByEmail(Email);
//	    List<FavoriteDto> dtoList = new ArrayList<>();
//    	    for (Favorite entity : list) {
//    	        FavoriteDto fDto = new FavoriteDto();
//
//    	        ThemeMap themeMap = entity.getThememapId();
//    	        ThemeMapDto themeMapDto = ThemeMapMapper.toDto(themeMap);
//
//    	        
//
//    	 
//
//
//
//    	        dtoList.add(fDto);
//    	    }
//
//    	    return dtoList;
//    	}
//
//}//    	
//    	return dtoList;
    	
    


