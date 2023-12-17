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
     * 테마지도 검색 o
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
     * 테마맵 생성 o
     * @param email
     * @param themeMapDto
     * @return
     * @throws FindException
     */
   
    public ThemeMapDto createThemeMap(String email, ThemeMapDto themeMapDto) throws FindException {
    	Optional<Member> optMember = memberRepository.findById(email);
    	Member findMember = optMember.orElseThrow(() -> new FindException("멤버가 없어용"));
    	
    	ThemeMap themeMap = ThemeMapMapper.toEntity(themeMapDto);
    	themeMap.setMemberEmail(findMember);
    	
        ThemeMap savedThemeMap = themeMapRepository.save(themeMap);
        return ThemeMapMapper.toDto(savedThemeMap);
    }
    
 // 테마맵 조회 (ID로) o
    public ThemeMapDto getThemeMapById(String email, Long themeMapId) throws FindException {
        Optional<Member> optMember = memberRepository.findById(email);
        Member findMember = optMember.orElseThrow(() -> new FindException("멤버가 없어용"));

        Optional<ThemeMap> optionalThemeMap = themeMapRepository.findByIdAndMemberEmail(themeMapId, findMember);
        if (optionalThemeMap.isPresent()) {
            ThemeMap themeMap = optionalThemeMap.get();
            return ThemeMapMapper.toDto(themeMap);
        } else {
            throw new FindException("테마맵을 찾을 수 없습니다. ID: " + themeMapId);
        }
    }

    // 테마맵 삭제o 
    public void deleteThemeMap(String email,Long themeMapId) {
        themeMapRepository.deleteById(themeMapId);
    }

 // 테마맵 수정 o
    public ThemeMapDto updateThemeMap(String email, Long themeMapId, ThemeMapDto themeMapDto) throws FindException {
        Optional<ThemeMap> optionalThemeMap = themeMapRepository.findById(themeMapId);
        if (optionalThemeMap.isPresent()) {
            ThemeMap existingThemeMap = optionalThemeMap.get();
            
            // 업데이트할 필요 있는 필드들을 설정
            existingThemeMap.setName(themeMapDto.getName());
            existingThemeMap.setColor(themeMapDto.getColor());
            existingThemeMap.setMemo(themeMapDto.getMemo());
            existingThemeMap.setMainmap(themeMapDto.getMainmap());
            existingThemeMap.setShow(themeMapDto.getShow());
             
            // 필요한 필드들을 업데이트
            ThemeMap updatedThemeMap = themeMapRepository.save(existingThemeMap);
            return ThemeMapMapper.toDto(updatedThemeMap);
        } else {
            throw new FindException("테마맵을 찾을 수 없습니다. ID: " + themeMapId);
        }
    }
    /*
     * auth 토큰으로 이메일을 받아와서 그 이메일에 해당하는 자신이 가진 모든 thememap을 list로 보여주기.
     * **/
    // 모든 테마맵 조회 o
    public List<ThemeMapDto> getAllThemeMaps(String email) throws FindException {
        Optional<Member> optMember = memberRepository.findById(email);
        if (optMember.isPresent()) {
            Member member = optMember.get();
            List<ThemeMap> themeMaps = themeMapRepository.findByMemberEmail(member);

            // ThemeMap을 ThemeMapDto로 변환
            List<ThemeMapDto> themeMapDtos = themeMaps.stream()
                .map(ThemeMapMapper::toDto)
                .collect(Collectors.toList());

                return themeMapDtos;
            } else {
            // 회원을 찾을 수 없는 경우 예외 처리 또는 빈 리스트 반환
                throw new FindException("회원을 찾을 수 없습니다.");
            }
    	
    	
    }
    
    // 검색한 리스트 중 한개를 나의 테마맵으로 추가
    public ThemeMapDto addToMyThemeMapList(String email, Long themeMapId) throws FindException {
        // 특정 ID의 테마맵 DTO 얻어오기
        ThemeMapDto themeMapDtoToAdd = getThemeMapDtoById(themeMapId);

        // 현재 사용자의 모든 테마맵 가져오기
        List<ThemeMapDto> myThemeMaps = getAllThemeMaps(email);

        // 이미 내 테마맵 리스트에 있는지 확인
        if (myThemeMaps.stream().anyMatch(map -> map.getId().equals(themeMapDtoToAdd.getId()))) {
            throw new FindException("이미 내 테마맵 리스트에 추가된 테마맵입니다.");
        }

        // 내 테마맵 리스트에 추가
        myThemeMaps.add(themeMapDtoToAdd);
        
        return themeMapDtoToAdd;
    }
    
 // 특정 ID의 테마맵 DTO 얻어오기
    private ThemeMapDto getThemeMapDtoById(Long themeMapId) throws FindException {
        Optional<ThemeMap> optionalThemeMap = themeMapRepository.findById(themeMapId);
        if (optionalThemeMap.isPresent()) {
            ThemeMap themeMap = optionalThemeMap.get();
            return ThemeMapMapper.toDto(themeMap);
        } else {
            throw new FindException("테마맵을 찾을 수 없습니다. ID: " + themeMapId);
        }
    }
 // 테마맵 복사 
    public ThemeMapDto copyThemeMap(String email, Long themeMapId) throws FindException {
        // 원본 테마맵 조회
        ThemeMapDto originalThemeMap = getThemeMapById(email, themeMapId);

        // 새로운 테마맵 생성 및 복사
        ThemeMapDto copiedThemeMap = createThemeMap(email, originalThemeMap);

        return copiedThemeMap;
    }
    
}