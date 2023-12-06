package com.kosa.mapbegood.domain.mymap.thememap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.service.ThemeMapService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/maplist")
public class ThemeMapListController {
	
	@Autowired
	private ThemeMapService themeMapService;
	
	@Autowired
	private AuthenticationUtil authenticationUtil;
	
	
	/* 설명: 테마지도에서 show가 되어 있는 것 
	 * (공개테마지도)중에 테마지도 이름으로 검색이 가능하게 해야한다.
	   검색할 리스트 이름이나 리스트 태그를 입력하세요.
		
	  */
	@GetMapping("/search") //o
	public ResponseEntity<List<ThemeMapDto>> searchThemeMap(Authentication authentication,
									  @RequestBody Map<String,String> map) {		
	//검색 서비스 호출 및 결과 반환
	//responseentity를 사용하는 이유 	
		try {
			String name = map.get("name");
			List<ThemeMapDto> list = themeMapService.searchThemeMap(name);
			log.error("이름으로 조회{}", list.size());
	        return ResponseEntity.ok(list);

		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	 
	
	
}