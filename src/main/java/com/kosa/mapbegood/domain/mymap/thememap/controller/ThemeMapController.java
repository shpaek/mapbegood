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
import com.kosa.mapbegood.domain.mymap.thememap.service.ThemeMapService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;


@RestController
@RequestMapping("/mymap")
public class ThemeMapController {

    @Autowired
    private ThemeMapService themeMapService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    //토큰으로 받아와서 setnickname 교체해주기
    // 테마맵 생성
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

    // 테마맵 조회 (ID로)
    @GetMapping("/{themeMapId}")
    public ResponseEntity<ThemeMapDto> getThemeMapById(Authentication authentication,
    													@PathVariable Long themeMapId)
    //throws FindException
    {
    	//String email = authenticationUtil.getUserEmail(authentication);
    	try {
			themeMapService.getThemeMapById(themeMapId);
			return ResponseEntity.ok(null);
		} catch (FindException e) {
			// TODO/ Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}    	///
    }

    
    // 테마맵 삭제
    @DeleteMapping("/delete/{themeMapId}")
    public ResponseEntity<String> deleteThemeMap(Authentication authentication,
    						@PathVariable Long themeMapId) throws FindException {
    	
    	String email = authenticationUtil.getUserEmail(authentication);
		themeMapService.deleteThemeMap(email, themeMapId);
		
		return ResponseEntity.ok("삭제 성공");
    }
   
    // 테마맵 수정
    @PutMapping("/update/{themeMapId}")
    public ThemeMapDto updateThemeMap(Authentication authentication,
    								@PathVariable Long themeMapId,
    								@RequestBody ThemeMapDto themeMapDto) {
    	String email = "test@mail.com";
    	Member m = new Member();
    	m.setEmail(email);
    	//-----------------------------
    	themeMapDto.setMemberEmail(m);
    	
    	return themeMapService.updateThemeMap(email, themeMapDto);
    }

    // 모든 테마맵 조회
    @GetMapping("/list")
    public List<ThemeMapDto> getAllThemeMaps() {
    	Member m = new Member();
    	m.setEmail("test@mail.com");
    	//-----------------------------
        return themeMapService.getAllThemeMaps(m);
    }
}
