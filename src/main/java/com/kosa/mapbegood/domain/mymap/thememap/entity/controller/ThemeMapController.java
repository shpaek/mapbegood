package com.kosa.mapbegood.domain.mymap.thememap.entity.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.service.MemberService;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.service.ThemeMapService;

@RestController
@RequestMapping("/mymap")
public class ThemeMapController {

    private final ThemeMapService themeService;
//    private final MemberService memberService;
    @Autowired
    public ThemeMapController(ThemeMapService thememapService) {//,MemberService member
        this.themeService = thememapService;
//        this.memberService= memberService;
        
    }

    @PostMapping
    public ThemeMap createThemeMap(@RequestBody ThemeMap themeMap) {
//    	String memberNickname =ThemeMap.getemberNickname();
//    	Member member= memberService.getMemberByNickname(memberNickname);
    	
    	
        return themeService.createThemeMap(themeMap);
    }

    @PutMapping("/{themeMapId}")
    public ThemeMap updateThemeMap(@PathVariable Long themeMapId, @RequestBody ThemeMap updatedThemeMap) {
        // 내테마지도 수정 엔드포인트
        return themeService.updateThemeMap(themeMapId, updatedThemeMap);
    }

    @DeleteMapping("/{themeMapId}")
    public void deleteThemeMap(@PathVariable Long themeMapId) {
        // 내테마지도 삭제 엔드포인트
        themeService.deleteThemeMap(themeMapId);
    }

    @GetMapping("/{themeMapId}")
    public List<ThemeMap> getAllThemeMaps() {
        // 모든 내테마지도 조회 엔드포인트
        return themeService.getAllThemeMaps();
    }
}