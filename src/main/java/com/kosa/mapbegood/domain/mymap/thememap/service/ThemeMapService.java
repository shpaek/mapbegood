package com.kosa.mapbegood.domain.mymap.thememap.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.mymap.thememap.repository.ThemeMapRepository;
import com.kosa.mapbegood.domain.mymap.util.ThemeMapMapper;
import com.kosa.mapbegood.exception.FindException;

@Service
public class ThemeMapService {

    @Autowired
    private ThemeMapRepository themeMapRepository;
    
    @Autowired
    private MemberRepository memberRepository;

    
    /**
     * 
     * 테마지도 검색 
     * 
     * 설명: 테마지도에서 show가 되어 있는 것(공개테마지도)중에 테마지돟이름으로 검색이 가능하게 해야한다.
     * @param email
     * @param themeMapDto
     * @return
     * @throws FindException
     */
     
    public List<ThemeMapDto> searchThemeMap(String name) throws FindException{//ThemeMapDto themeMapDto) {
//        if (themeMapDto.getName() != null && !themeMapDto.getName().isEmpty()) {
            //  이름으로 검색
            List<ThemeMap> searchResults = themeMapRepository.findByNameContainingAndShowTrue(name);//themeMapDto.getName());
            if(searchResults.size() == 0) {
            	throw new FindException("이름에 해당하는 테마지도가 없습니다");
            }
            return searchResults.stream()
                    .map(ThemeMapMapper::toDto)
                    .collect(Collectors.toList());//스트림 결과를 리스트로 수집
//        } else {
//            return null;
//        }
    }
    
    
    /**
     * 테마지도 내의 장소 복붙 
     * 설명: 공개테마지도에서 마음에 드는 테마지도를 복사 붙혀넣기 한다.
     * @param email
     * @param themeMapDto
     * @return
     * @throws FindException
     */
    public ThemeMapDto copyMyplace( ){
    	
    	return null; 
    }  
    /**
     * 설명: 공개 테마지도(show가true인 것)중에서 나의 장소로 리스트가 생성 가능하게 해야한다.
     * 테마지도 내 장소로 새 리스트 생성
     * @param email
     * @param themeMapDto
     * @return
     * @throws FindException
     */
    public ThemeMapDto bringMyPlace() {
    	return null; 

    }
    
    
    
    
    /**
     * 테마맵 생성
     * @param email
     * @param themeMapDto
     * @return
     * @throws FindException
     */
   
    public ThemeMapDto createThemeMap(String email, ThemeMapDto themeMapDto) throws FindException {
    	Optional<Member> optMember = memberRepository.findById(email);
    	Member findMember = optMember.orElseThrow(() -> new FindException());
    	
    	ThemeMap themeMap = ThemeMapMapper.toEntity(themeMapDto);
    	themeMap.setMemberEmail(findMember);
    	
        ThemeMap savedThemeMap = themeMapRepository.save(themeMap);
        return ThemeMapMapper.toDto(savedThemeMap);
    }

    // 테마맵 조회 (ID로)
    public ThemeMapDto getThemeMapById(Long themeMapId) throws FindException{
//    	Optional<Member> optMember = memberRepository.findById(email);
//    	Member findMember = optMember.orElseThrow(() -> new FindException());
//
//        if(optDto.isPresent()){
//        	ThemeMapDto dto = optDto.get();
//        	dto.getMemberEmail().setPassword(null);
//        	return dto;
//        }else{
//        	throw new FindException("테마지도가 없습니다");
//        }
        //return optionalThemeMap.map(ThemeMapMapper::toDto).orElse(null);
    	Optional<ThemeMap> opttm = themeMapRepository.findById(themeMapId);
    	if(opttm.isPresent()) {
    		ThemeMap tm = opttm.get();
    		return ThemeMapMapper.toDto(tm);
    		
    	}else {
    		throw new FindException("테마지도가 없습니다");
    	}
    }

    // 테마맵 삭제
    public void deleteThemeMap(String email,Long themeMapId) {
        themeMapRepository.deleteById(themeMapId);
    }

    // 테마맵 수정
    public ThemeMapDto updateThemeMap(String email,ThemeMapDto themeMapDto) {
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
		return themeMapDto;
      
    }

    // 모든 테마맵 조회
    public List<ThemeMapDto> getAllThemeMaps(Member m) {
        List<ThemeMap> themeMapList = themeMapRepository.findByMemberEmail(m);
        return themeMapList.stream()
                .map(ThemeMapMapper::toDto)
                .collect(Collectors.toList());
    }
}