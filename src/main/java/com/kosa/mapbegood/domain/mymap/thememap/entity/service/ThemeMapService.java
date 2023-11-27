package com.kosa.mapbegood.domain.mymap.thememap.entity.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;

@Service
public class ThemeMapService {

    private final ThemeMapRepository tmr;

    @Autowired
    public ThemeMapService(ThemeMapRepository thememapRepository) {
        this.tmr = thememapRepository;
    }

    public ThemeMap createThemeMap(ThemeMap themeMap) {
    	
    	System.out.println(themeMap.getMemberNickname());
    	System.out.println(themeMap.getName());
    	System.out.println(themeMap.getColor());
    	System.out.println(themeMap.getMemo());
    	System.out.println(themeMap.getShow());
    	System.out.println(themeMap.getMainmap());
    	
    	// 내테마지도 생성 로직
        return tmr.save(themeMap);
    }

    public ThemeMap updateThemeMap(Long themeMapId, ThemeMap updatedThemeMap) {
        // 내테마지도 수정 로직
        ThemeMap existingThemeMap = tmr.findById(themeMapId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 테마지도를 찾을 수 없습니다."));
        
        // 수정할 내용 업데이트
        existingThemeMap.setName(updatedThemeMap.getName());
        existingThemeMap.setColor(updatedThemeMap.getColor());
        existingThemeMap.setMemo(updatedThemeMap.getMemo());
        existingThemeMap.setShow(updatedThemeMap.getShow());
        existingThemeMap.setMainmap(updatedThemeMap.getMainmap());
        
        // 내테마지도 저장
        return tmr.save(existingThemeMap);
    }
    
    public void deleteThemeMap(Long themeMapId) {
        // 내테마지도 삭제 로직
        tmr.deleteById(themeMapId);
    }

    public List<ThemeMap> getAllThemeMaps() {
        // 모든 내테마지도 조회 로직
        return tmr.findAll();
    }
}