package com.kosa.mapbegood.domain.mymap.myplace.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.myplace.mapper.MyplaceMapper;
import com.kosa.mapbegood.domain.mymap.myplace.repository.MyplaceRepository;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.mapper.MyplaceFeedMapper;
import com.kosa.mapbegood.domain.mymap.thememap.repository.ThemeMapRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class MyplaceService {
	@Autowired
	private MyplaceRepository mpr;

	@Autowired
	private ThemeMapRepository tmr;

	@Autowired
	private MyplaceMapper mapper;
	
	@Autowired
	private MyplaceFeedMapper mfMapper;

	/**
	 * 내 테마지도에 저장된 마이플레이스를 조회한다
	 * @param themeMapId 테마지도id
	 * @return 마이플레이스 리스트
	 */
	public List<MyplaceDTO> findAllMyplace(Long themeMapId) throws FindException{
		List<Myplace> myplaceList = mpr.findByThememapId_Id(themeMapId);
		List<MyplaceDTO> myplaceDtoList = new ArrayList<MyplaceDTO>();
		for(Myplace myplace: myplaceList) {
			MyplaceDTO myplaceDto = mapper.entityToDto(myplace);
			MyplaceFeed myfeed = myplace.getFeed();
			MyplaceFeedDTO myfeedDto = mfMapper.entityToDto(myfeed);
			myplaceDto.setFeed(myfeedDto);
			myplaceDtoList.add(myplaceDto);
		}
		return myplaceDtoList;
	}

	/**
	 * 마이플레이스(장소)의 상세정보를 조회한다
	 * @param myplaceId
	 * @return
	 * @throws FindException
	 */
	public MyplaceDTO findMyplace(Long myplaceId) throws FindException{
		Optional<Myplace> myplace = mpr.findById(myplaceId);
		if (myplace.isPresent()) {
			return mapper.entityToDto(myplace.get());	
		} else {
		    return null;
		}
	}

	/** 
	 * 내 테마지도에 장소를 추가한다
	 * @param myplaceDto
	 */
	public void createMyplace(MyplaceDTO myplaceDto) throws AddException {
	    List<Myplace> myplaceList = mpr.findByThememapId_Id(myplaceDto.getThememapId().getId());
	    
	    for (Myplace myplace : myplaceList) {
	        System.out.println(myplace.getPlaceId().getId() + "/" + myplaceDto.getPlaceId().getId());
	        if (myplace.getPlaceId().getId().equals(myplaceDto.getPlaceId().getId())) {
	            // place_id가 같은 경우에는 이미 저장된 것이므로 더 이상 진행하지 않고 메소드 종료
	            return;
	        }
	    }

	    // 위의 for 루프에서 return이 실행되지 않았다면, 즉 중복된 place_id가 없다면 save 실행
	    mpr.save(mapper.dtoToEntity(myplaceDto));
	}

	public void updateMyplace(MyplaceDTO myplaceDto) throws ModifyException {
		Optional<Myplace> existingmyplace = mpr.findById(myplaceDto.getId());

		if (existingmyplace.isPresent()) {
			Myplace myplace = existingmyplace.get();
//			Date sqlDate = new Date(.getTime());
			myplace.setVisitedAt(myplaceDto.getVisitedAt());
			
			mpr.save(myplace);
		}else {
			throw new ModifyException("해당 ID에 대한 our 플레이스를 찾을 수 없습니다.");
		}
	}
	
	/**
	 * 내 테마지도에 등록된 장소를 삭제한다
	 * @param myplaceId
	 */
	public void deleteMyplace(Long myplaceId) throws RemoveException {
	    Optional<Myplace> mp = mpr.findById(myplaceId);
	    Myplace myplace = mp.get();
	    if (mp.isPresent()) {
	        mpr.delete(myplace);
	    } else {
	        throw new RemoveException("마이플레이스 삭제 못했대요 ㅋㅋ");
	    }
	}

	//공개 리스트인지 확인하는 코드 추가해야함
	/**
	 * 테마지도 내의 장소 복사 붙여넣기
	 * @param openThemeMapId
	 * @param mythemeMapId
	 * @throws AddException
	 * @throws FindException
	 */
	public void mergeToMyThemeMap(Long openThemeMapId, Long mythemeMapId) throws AddException, FindException {
		if(tmr.existsShowById(openThemeMapId)){
			List<MyplaceDTO> openPlaceList = findAllMyplace(openThemeMapId);
			List<MyplaceDTO> myPlaceList = findAllMyplace(mythemeMapId);
			for(MyplaceDTO openPlace : openPlaceList) {
				if(myPlaceList.contains(openPlace.getPlaceId())) {
					return;
				}else {
					MyplaceDTO copyDto = new MyplaceDTO();
					copyDto.setPlaceId(openPlace.getPlaceId());
					ThemeMapDto thememapDto = new ThemeMapDto();
					thememapDto.setId(mythemeMapId);
					copyDto.setThememapId(thememapDto);
					createMyplace(copyDto);
				}
			}
		}else {
			throw new AddException("장소를 추가할 수 없습니다");
		}
	}

}
