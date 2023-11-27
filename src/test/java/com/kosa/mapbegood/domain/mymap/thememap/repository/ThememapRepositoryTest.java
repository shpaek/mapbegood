package com.kosa.mapbegood.domain.mymap.thememap.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.entity.repository.ThemeMapRepository;

@SpringBootTest
public class ThememapRepositoryTest {

	@Autowired
	private ThemeMapRepository repository;
	
	@Test
	public void selectThememap() {
	    // 조회할 ThemeMap의 ID
	    long themeMapId = 1; 

	    // optional<ThemeMap>은 값이 null이 될 수 있는 객체를 감싸는 래퍼 클래스임.
	    Optional<ThemeMap> optionalThemeMap = repository.findById(themeMapId);

	    // 테마 맵이 존재하는 경우
	    if (optionalThemeMap.isPresent()) {
	        ThemeMap selectedThemeMap = optionalThemeMap.get();
	        System.out.println("조회했습니다.");
	    } else {
	        System.out.println("존재하지 않습니다.");
	    }
	}
	@Test
	public void insertThememap() {
		
		ThemeMap tmp = new ThemeMap();	
		Member member = new Member();
		member.setNickname("test");
		tmp.setMemberNickname(member);
		tmp.setName("test");
		tmp.setColor("blue");
		tmp.setMainmap(true);
		tmp.setShow(true);
//		tmp.setMemo("하이");
		
		repository.save(tmp);
	}
	
	@Test
	public void updateThememap() {
		int themeMapId=1;
		
		//optional<ThemeMap>은 값이 null이 될수 있는 객체를 감싸는 래퍼클래스임.
		
		Optional<ThemeMap> optionalThemeMap=repository.findById((long)themeMapId);
		
		if(optionalThemeMap.isPresent()) {
			ThemeMap updatetmp=optionalThemeMap.get();
			updatetmp.setName("dd2");
			repository.save(updatetmp);
		}
	}
	@Test
	public void deleteThememap() {
		int themeMapId=1;
		
		Optional<ThemeMap> optionalThemeMap=repository.findById((long)themeMapId);
	

		if(optionalThemeMap.isPresent()) {
			ThemeMap deletetmp=optionalThemeMap.get();
			  // 테마 맵 삭제
	        repository.delete(deletetmp);
		}
	}
}
