package com.kosa.mapbegood.domain.mymap.favorite.entity.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.service.MemberService;
import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.FavoriteEmbedded;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.FavoriteDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.MemberDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.MyPlaceDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.repository.FavoriteRepository;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ThemeMapRepository themeMapRepository;
    private final MemberService memberService;

    @Autowired
    public FavoriteService(
            FavoriteRepository favoriteRepository,
            ThemeMapRepository themeMapRepository,
            MemberService memberService) {
        this.favoriteRepository = favoriteRepository;
        this.themeMapRepository = themeMapRepository;
        this.memberService = memberService;
    }
    public void createFavorite(FavoriteDto favoriteDto) {
        Favorite favorite = new Favorite();
        FavoriteEmbedded id = new FavoriteEmbedded();

        // ThemeMap 및 Member를 찾아서 설정
        id.setThememapId(favoriteDto.getThemeMap().getId()); // FavoriteDto에서 ThemeMapDto를 가져오는 방법은 favoriteDto.getThemeMap()를 통해 접근합니다.
        id.setNickname(favoriteDto.getMember().getNickname());
        favorite.setId(id);

        ThemeMap themeMap = themeMapRepository.findById(favoriteDto.getThemeMap().getId()).orElse(null);
       // Member member = MemberService.findByNickname(FavoriteDto.getMember().getNickname());
        favorite.setThememapId(themeMap);
        //favorite.setMemberNickname(member);

     

        favoriteRepository.save(favorite);
    }

//dto로 변환작업하고 , dto로 리턴하기.
    public List<FavoriteDto> selectFavoriteList(String nickname) {
    	List<Favorite> list = favoriteRepository.findFavoritesByNickname(nickname);
    	List<FavoriteDto> dtoList = new ArrayList<>();
    	for(Favorite entity: list) {
    		FavoriteDto fDto = new FavoriteDto();
    		Member m = entity.getMemberNickname();
    		MemberDto mdto = new MemberDto();
    		//mdto.setNickname(nickname);
//    		mdto.setPassword(m.getPassword());
    		//mdto.setEmail(m.getEmail());
    		//mdto.setCreatedAt(m.getCreatedAt());
    		//fDto.setMember(mdto);
    		//-------------------
    		
    		
    		ThemeMap themeMap =  entity.getThememapId();
    		ThemeMapDto themeMapDto = new ThemeMapDto();
    		themeMapDto.setId(themeMap.getId());
    		themeMapDto.setColor(themeMap.getColor());
    		themeMapDto.setMainmap(themeMap.getMainmap());
    		
    		themeMapDto.setMemo(themeMap.getMemo());
    		themeMapDto.setName(themeMap.getName());
    		themeMapDto.setShow(themeMap.getShow());
    		List<MyPlaceDto>mpDtoList = new ArrayList<>();
    		themeMapDto.setMyplaceList(mpDtoList);
    		for(Myplace mp: themeMap.getMyplaceList()) {
    			MyPlaceDto mpDto = new MyPlaceDto();
    			//mpDto.set~~~
    			mpDtoList.add(mpDto);
    		}
    		
    		fDto.setThemeMap(themeMapDto);
    		//----------------------

    		java.sql.Date dt = java.sql.Date.valueOf(entity.getCreatedAt());    		
    		fDto.setCreateat(dt);
    		//------------------------
    		dtoList.add(fDto);    		
    		
    		
    	}
    	
    	return dtoList;
    	
    }
}

