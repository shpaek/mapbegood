package com.kosa.mapbegood.domain.mymap.myplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.repository.MyplaceRepository;

@Service
public class MyplaceService {
	@Autowired
	private MyplaceRepository mpr;
	
	/**
	 * 내 테마지도에 저장된 마이플레이스를 조회한다
	 * @param themeMapId 테마지도id
	 * @return 마이플레이스 리스트
	 */
	public List<MyplaceDTO> findAllMyplace(Long themeMapId){
		return mpr.findBythemeMapId(themeMapId);

	}
	
	

}
