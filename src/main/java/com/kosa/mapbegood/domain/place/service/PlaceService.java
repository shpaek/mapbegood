package com.kosa.mapbegood.domain.place.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.entity.Place;
import com.kosa.mapbegood.domain.place.mapper.PlaceMapper;
import com.kosa.mapbegood.domain.place.repository.PlaceRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository pr;

	@Autowired
	private PlaceMapper mapper;


	/**
	 *  place 상세 정보 조회
	 * @param placeId 플레이스아이디
	 * @return placeDTO
	 * @throws FindException
	 */
	public PlaceDTO findPlace(Long placeId) throws FindException{
		Optional<Place> place = pr.findById(placeId);
		if (place.isPresent()) {
		    return mapper.entityToDto(place.get());
		} else {
		    return null;
		}
	}

	/**
	 * place 생성
	 * @param placeDto 
	 * @throws AddException
	 */
	public void createPlace(PlaceDTO placeDto) throws AddException{
		pr.save(mapper.dtoToEntity(placeDto));
	}

	/**
	 * place 삭제
	 * @param placeId
	 * @throws RemoveException
	 */
	public void deletePlace(Long placeId) throws RemoveException{
		Optional<Place> p = pr.findById(placeId);
		Place place = p.get();
		if(p.isPresent()) {
			pr.delete(place);
		}else {
			throw new RemoveException("플레이스 삭제 못했음");					
		}
	}

}
