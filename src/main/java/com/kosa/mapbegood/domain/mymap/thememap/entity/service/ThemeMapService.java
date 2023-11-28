 package com.kosa.mapbegood.domain.mymap.thememap.entity.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;
import com.kosa.mapbegood.domain.mymap.util.ThemeMapMapper;

import com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite;
import com.kosa.mapbegood.domain.mymap.favorite.entity.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;
import com.kosa.mapbegood.domain.mymap.util.ThemeMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThemeMapService {

    @Autowired
    private ThemeMapRepository themeMapRepository;

    // 테마맵 생성
    public ThemeMapDto createThemeMap(ThemeMapDto themeMapDto) {
        ThemeMap themeMap = ThemeMapMapper.toEntity(themeMapDto);
        ThemeMap savedThemeMap = themeMapRepository.save(themeMap);
        return ThemeMapMapper.toDto(savedThemeMap);
    }

    // 테마맵 조회 (ID로)
    public ThemeMapDto getThemeMapById(Long themeMapId) {
        Optional<ThemeMap> optionalThemeMap = themeMapRepository.findById(themeMapId);
        return optionalThemeMap.map(ThemeMapMapper::toDto).orElse(null);
    }

    // 테마맵 삭제
    public void deleteThemeMap(Long themeMapId) {
        themeMapRepository.deleteById(themeMapId);
    }

    // 테마맵 수정
    public ThemeMapDto updateThemeMap(ThemeMapDto themeMapDto) {
        Optional<ThemeMap> optionalThemeMap = themeMapRepository.findById(themeMapDto.getId());
        if (optionalThemeMap.isPresent()) {
            ThemeMap existingThemeMap = optionalThemeMap.get();
            // 업데이트할 필요 있는 필드들을 설정
            existingThemeMap.setName(themeMapDto.getName());
            existingThemeMap.setColor(themeMapDto.getColor());
            existingThemeMap.setMemo(themeMapDto.getMemo());
            existingThemeMap.setMainmap(themeMapDto.getShow());
            existingThemeMap.setShow(themeMapDto.getShow());
             

            // 필요한 필드들을 업데이트
            ThemeMap updatedThemeMap = themeMapRepository.save(existingThemeMap);
            return ThemeMapMapper.toDto(updatedThemeMap);
        }
      
    }

    // 모든 테마맵 조회
    public List<ThemeMapDto> getAllThemeMaps() {
        List<ThemeMap> themeMapList = themeMapRepository.findAll();
        return themeMapList.stream()
                .map(ThemeMapMapper::toDto)
                .collect(Collectors.toList());
    }
}