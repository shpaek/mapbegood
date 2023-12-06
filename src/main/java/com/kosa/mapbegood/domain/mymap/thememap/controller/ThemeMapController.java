package com.kosa.mapbegood.domain.mymap.thememap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.dto.themeMapDTO;
import com.kosa.mapbegood.domain.mymap.thememap.service.ThemeMapService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mymap")
public class ThemeMapController {

    @Autowired
    private ThemeMapService themeMapService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    
    //토큰으로 받아와서 setnickname 교체해주기
    // 테마맵 생성 o
    @PostMapping("/create")
    public ThemeMapDto createThemeMap(Authentication authentication,
    								  @RequestBody ThemeMapDto themeMapDto) {
    	String email = authenticationUtil.getUserEmail(authentication);
    	//-------------------------
//    	Member m = new Member();
//    	m.setEmail("test@mail.com");
    	//-----------------------------
//    	themeMapDto.setMemberEmail(m);
    	try {
			return themeMapService.createThemeMap(email, themeMapDto);
		} catch (FindException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
 
    
    // 테마맵 삭제    o favorite자식 테이블의 매핑 문제 같은데 모르겠음
    @DeleteMapping("/delete/{themeMapId}")
    public ResponseEntity<String> deleteThemeMap(Authentication authentication,
    						@PathVariable Long themeMapId) throws FindException {
    	
    		String email = authenticationUtil.getUserEmail(authentication);
    		log.error("email은 갖고옴"+email);
    		log.error("thememap은?"+themeMapId);
    		//두개는 갖고오는데 sql이 오류가 뜨는데 왜 favorite 테이블이 오류나는지 
    		
    		themeMapService.deleteThemeMap(email, themeMapId);
		
    		return ResponseEntity.ok("삭제 성공");
//    }catch(FindException e){
//    		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    
   //테마맵 수정 o
    @PutMapping("/update/{themeMapId}")
    public ResponseEntity<ThemeMapDto> updateThemeMap(Authentication authentication,
                                                      @PathVariable Long themeMapId,   
                                                      @RequestBody ThemeMapDto themeMapDto) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            ThemeMapDto updatedThemeMap = themeMapService.updateThemeMap(email, themeMapId, themeMapDto);
            return ResponseEntity.ok(updatedThemeMap);
        } catch (FindException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    

    // 테마맵 조회 o
    @GetMapping("/list")
    public ResponseEntity<List<ThemeMapDto>> getAllThemeMaps(Authentication authentication) {
    	String email = authenticationUtil.getUserEmail(authentication);
    	 try {
             List<ThemeMapDto> themeMapDtos = themeMapService.getAllThemeMaps(email);
             return ResponseEntity.ok(themeMapDtos);
         } catch (FindException e) {
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
     }
    
 // 테마맵 조회 (ID로) o
    @GetMapping("/{themeMapId}")
    public ResponseEntity<ThemeMapDto> getThemeMapById(Authentication authentication,
                                                      @PathVariable Long themeMapId) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            ThemeMapDto themeMapDto = themeMapService.getThemeMapById(email, themeMapId);
            return ResponseEntity.ok(themeMapDto);
        } catch (FindException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
  }
  
