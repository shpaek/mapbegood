package com.kosa.mapbegood.domain.mymap.thememap.entity.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.service.MemberService;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.service.ThemeMapService;

 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/mymap")
public class ThemeMapController {

    @Autowired
    private ThemeMapService themeMapService;

    // 테마맵 생성
    @PostMapping("/create")
    public ThemeMapDto createThemeMap(@RequestBody ThemeMapDto themeMapDto) {
        return themeMapService.createThemeMap(themeMapDto);
    }

    // 테마맵 조회 (ID로)
    @GetMapping("/{themeMapId}")
    public ThemeMapDto getThemeMapById(@PathVariable Long themeMapId) {
        return themeMapService.getThemeMapById(themeMapId);
    }

    // 테마맵 삭제
    @DeleteMapping("/delete/{themeMapId}")
    public void deleteThemeMap(@PathVariable Long themeMapId) {
        themeMapService.deleteThemeMap(themeMapId);
    }

    // 테마맵 수정
    @PutMapping("/update/{themeMapId}")
    public ThemeMapDto updateThemeMap(@RequestBody ThemeMapDto themeMapDto) {
        return themeMapService.updateThemeMap(themeMapDto);
    }

    // 모든 테마맵 조회
    @GetMapping("/list")
    public List<ThemeMapDto> getAllThemeMaps() {
        return themeMapService.getAllThemeMaps();
    }
}
