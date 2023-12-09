package com.kosa.mapbegood.domain.mymap.myplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace;
import com.kosa.mapbegood.domain.mymap.myplace.mapper.MyplaceMapper;
import com.kosa.mapbegood.domain.mymap.myplace.repository.MyplaceRepository;
import com.kosa.mapbegood.domain.mymap.thememap.repository.ThemeMapRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class MyplaceService {
	@Autowired
	private MyplaceRepository mpr;

	@Autowired
	private ThemeMapRepository tmr;

	@Autowired
	private MyplaceMapper mapper;

	/**
	 * 내 테마지도에 저장된 마이플레이스를 조회한다
	 * @param themeMapId 테마지도id
	 * @return 마이플레이스 리스트
	 */
	public List<MyplaceDTO> findAllMyplace(Long themeMapId) throws FindException{
		List<Myplace> myplaceList = mpr.findBythememapId(themeMapId);
		List<MyplaceDTO> myplaceDtoList = new ArrayList<MyplaceDTO>();
		for(Myplace myplace: myplaceList) {
			myplaceDtoList.add(mapper.entityToDto(myplace));
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
		return mapper.entityToDto(myplace.get());
	}

	/** 
	 * 내 테마지도에 장소를 추가한다
	 * @param myplaceDto
	 */
	public void createMyplace(MyplaceDTO myplaceDto) throws AddException{
		mpr.save(mapper.dtoToEntity(myplaceDto));
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
		System.out.println("1");
		if(tmr.existsShowById(openThemeMapId)){
			System.out.println("2");
			List<MyplaceDTO> openPlaceList = findAllMyplace(openThemeMapId);
			System.out.println(openPlaceList);
			List<MyplaceDTO> myPlaceList = findAllMyplace(mythemeMapId);
			System.out.println(openPlaceList);
			for(MyplaceDTO openPlace : openPlaceList) {
				if(myPlaceList.contains(openPlace.getPlaceId())) {
					return;
				}else {
					createMyplace(openPlace);
				}
			}
		}else {
			throw new AddException("장소를 추가할 수 없습니다");
		}
	}

}
