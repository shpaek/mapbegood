package com.kosa.mapbegood.domain.mymap.favorite.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.kosa.mapbegood.exception.AddException;
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
    /*
     * 멤버의 이메일과 thememap의 id를 사용해서 즐겨찾기에 추가하기.
     * **/
    public void createFavorite(Long thememapId, String userEmail) {
        try {
            Member member = memberrepository.findByEmail(userEmail)
                    .orElseThrow(() -> new FindException("멤버가 없습니당"));

            ThemeMap themeMap = themeMapRepository.findById(thememapId)
                    .orElseThrow(() -> new FindException("테마맵이 없어용"));

            Favorite favorite = new Favorite();
            favorite.getId().setThememapId(thememapId);
            favorite.getId().setEmail(userEmail);
            favorite.setThememapId(themeMap);
            favorite.setMemberEmail(member);

            favoriteRepository.saveAndFlush(favorite);
        } catch (FindException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
    /*
     * 멤버의 이메일과 thememap의 id를 사용해서 즐겨찾기에 추가한 것 조회.
     * **/
    public List<FavoriteDto> getAllFavoritesForUser(String userEmail) {
        try {
            Member member = memberrepository.findByEmail(userEmail)
                    .orElseThrow(() -> new FindException("멤버를 찾을 수 없습니다."));

            List<Favorite> favorites = favoriteRepository.findByMemberEmail(member);

            return favorites.stream()
                    .map(this::mapFavoriteEntityToDto)
                    .collect(Collectors.toList());
        } catch (FindException e) {
            e.printStackTrace();
            // 필요에 따라 예외 처리
            return Collections.emptyList();
        }
    }

private FavoriteDto mapFavoriteEntityToDto(Favorite favorite) {
    FavoriteDto favoriteDto = new FavoriteDto();
    
    // ThemeMap 엔터티를 DTO로 매핑하는 mapThemeMapEntityToDto 메서드가 있다고 가정합니다.
    ThemeMapDto themeMapDto = mapThemeMapEntityToDto(favorite.getThememapId());

    // FavoriteDto에 ThemeMapDto 설정
    favoriteDto.setThemeMapDto(themeMapDto);
    
    // 다른 속성 설정
    
    return favoriteDto;
}

private ThemeMapDto mapThemeMapEntityToDto(ThemeMap themeMap) {
    if (themeMap != null) {
        ThemeMapDto themeMapDto = new ThemeMapDto();
        // ThemeMapDto 반환될 값 설정 
//        themeMapDto.setId(themeMap.getId());
        themeMapDto.setName(themeMap.getName());//
        themeMapDto.setMemo(themeMap.getMemo());
        themeMapDto.setColor(themeMap.getColor());
        themeMapDto.setMainmap(themeMap.getMainmap());
        // 나머지 속성들도 필요에 따라 추가

        return themeMapDto;
    } else {
        return null; // 혹은 빈 ThemeMapDto를 반환하거나, 예외 처리를 할 수 있습니다.
    }
}
}
 


