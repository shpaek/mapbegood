package com.kosa.mapbegood.domain.mymap.thememap.entity.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.service.ThemeMapService;
import com.kosa.mapbegood.exception.FindException;
@RestController
@RequestMapping("/mymap")
public class ThemeMapController {

    @Autowired
    private ThemeMapService themeMapService;

    //
    
    
    
    
    //토큰으로 받아와서 setnickname 교체해주기
    // 테마맵 생성
    @PostMapping("/create")
    public ThemeMapDto createThemeMap(@RequestBody ThemeMapDto themeMapDto) {
    	//-------------------------
    	Member m = new Member();
    	m.setNickname("test2");
    	//-----------------------------
    	themeMapDto.setMemberNickname(m);
    	return themeMapService.createThemeMap(themeMapDto);
    }

    // 테마맵 조회 (ID로)
    @GetMapping("/{themeMapId}")
    public ResponseEntity<ThemeMapDto> getThemeMapById(@PathVariable Long themeMapId) {
    	try {
    		return ResponseEntity.ok( themeMapService.getThemeMapById(themeMapId));
    	}catch(FindException e) {
    		return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST );
    	}
    }

    // 테마맵 삭제
    @DeleteMapping("/delete/{themeMapId}")
    public void deleteThemeMap(@PathVariable Long themeMapId) {
        themeMapService.deleteThemeMap(themeMapId);
    }

    // 테마맵 수정
    @PutMapping("/update/{themeMapId}")
    public ThemeMapDto updateThemeMap(@RequestBody ThemeMapDto themeMapDto) {
    	Member m = new Member();
    	m.setNickname("test2");
    	//-----------------------------
    	themeMapDto.setMemberNickname(m);
    	
    	return themeMapService.updateThemeMap(themeMapDto);
    }

    // 모든 테마맵 조회
    @GetMapping("/list")
    public List<ThemeMapDto> getAllThemeMaps() {
    	Member m = new Member();
    	m.setNickname("test2");
    	//-----------------------------
        return themeMapService.getAllThemeMaps(m);
    }
}
