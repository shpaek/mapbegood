package com.kosa.mapbegood.domain.ourmap.ourplace.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.service.MyplaceService;
import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;
import com.kosa.mapbegood.domain.ourmap.ourplace.mapper.OurplaceMapper;
import com.kosa.mapbegood.domain.ourmap.ourplace.repository.OurplaceRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

public class OurplaceService {

	@Autowired
	private OurplaceRepository opr;
	
	@Autowired
	private MyplaceService mps;

	@Autowired
	private OurplaceMapper mapper;


	/**
	 * 그룹 테마지도에 저장된 Ourplace를 조회한다
	 * @param groupThemeMapId 그룹테마지도id
	 * @return Ourplace 리스트
	 * @throws FindException
	 */
	public List<OurplaceDTO> findAllOurplace(Long groupThemeMapId) throws FindException{
		GroupThememap groupThememap = new GroupThememap();
		groupThememap.setId(groupThemeMapId);
		List<Ourplace> ourplacelist = opr.findByGroupThememapId_Id(groupThememap);
		List<OurplaceDTO> ourplaceDtoList = new ArrayList<OurplaceDTO>(); 	
		for(Ourplace ourplace: ourplacelist) {
			ourplaceDtoList.add(mapper.entityToDto(ourplace));
		}
		return ourplaceDtoList;
	}
	
	
	/**
	 * ourplace의 상세정보를 조회
	 * @param ourplaceId
	 * @return
	 * @throws FindException
	 */
	public OurplaceDTO findOurplace(Long ourplaceId) throws FindException {
		Optional<Ourplace> ourplace = opr.findById(ourplaceId);
		if(ourplace.isPresent()) {
			return mapper.entityToDto(ourplace.get());		
		}else {
			return null;
		}
	}
	

	/**
	 * 그룹 테마지도에 장소를 추가한다
	 * @param ourplaceDto 
	 * @throws AddException
	 */
	public void createOurplace(OurplaceDTO ourplaceDto) throws AddException {
		opr.save(mapper.dtoToEntity(ourplaceDto));		
	}

	public void deleteOurplace(Long ourplaceId) throws RemoveException {
		Optional<Ourplace> op = opr.findById(ourplaceId);
		Ourplace ourplace = op.get();
		if(op.isPresent()) {
			opr.delete(ourplace);
		}else {
			throw new RemoveException("our 플레이스 삭제 못했대요 ㅋㅋ");
		}
	}

	public void updateOurplace(OurplaceDTO ourplaceDto) throws ModifyException {
		Optional<Ourplace> existingOurplace = opr.findById(ourplaceDto.getId());

		if (existingOurplace.isPresent()) {
			Ourplace ourplace = existingOurplace.get();
			Date sqlDate = new Date(ourplaceDto.getVisitedAt().getTime());
			ourplace.setVisitedAt(sqlDate);

			opr.save(ourplace);
		}else {
			throw new ModifyException("해당 ID에 대한 our 플레이스를 찾을 수 없습니다.");
		}
	}
	
	
	public void mergeToMyThemeMap(Long groupThemeMapId, Long mythemeMapId) throws FindException, AddException {
		List<OurplaceDTO> ourPlaceList = findAllOurplace(groupThemeMapId);
		List<MyplaceDTO> myPlaceList = mps.findAllMyplace(mythemeMapId);
		for(OurplaceDTO ourPlace : ourPlaceList) {
			if(myPlaceList.contains(ourPlace.getPlaceId())) {
				return;				
			}else {
				MyplaceDTO copyDto = new MyplaceDTO();
				copyDto.setPlaceId(ourPlace.getPlaceId());
				ThemeMapDto thememapDto = new ThemeMapDto();
				thememapDto.setId(mythemeMapId);
				copyDto.setThememapId(thememapDto);
				mps.createMyplace(copyDto);
			}
		}
	}
	
	

}
