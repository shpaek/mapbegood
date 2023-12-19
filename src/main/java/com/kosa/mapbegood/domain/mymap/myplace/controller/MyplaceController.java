package com.kosa.mapbegood.domain.mymap.myplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceWrapperDTO;
import com.kosa.mapbegood.domain.mymap.myplace.service.MyplaceService;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.service.PlaceService;
import com.kosa.mapbegood.exception.FindException;

@RestController
@RequestMapping("/myplace")
public class MyplaceController {
	
	@Autowired
	MyplaceService mps;	
	
	@Autowired
	PlaceService ps;

	@GetMapping("/{thememapId}")
	public ResponseEntity<?> findAllMyPlace(@PathVariable Long thememapId) throws FindException{
		try {
			List<MyplaceDTO> myplace = mps.findAllMyplace(thememapId);
				return ResponseEntity.ok(myplace);				
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * Myplace에 저장된 장소 정보 조회
	 * @param themeMapId
	 * @param mythemeMapId
	 * @return
	 */
	@GetMapping("/detail/{myplaceId}")                    
	public ResponseEntity<?> findMyPlace(@PathVariable Long myplaceId) throws FindException{
		try {
			MyplaceDTO myplace = mps.findMyplace(myplaceId);
			if(myplace!=null) {
				return ResponseEntity.ok(myplace);				
			}else {
				return ResponseEntity.ok(new FindException("못찾음"));
			}
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping("")
	ResponseEntity<?> createMyplace(@RequestBody MyplaceWrapperDTO myplaceWrapperDto){
		try {
			MyplaceDTO myplaceDto = myplaceWrapperDto.getMyplaceDto();
			PlaceDTO placeDto = myplaceWrapperDto.getPlaceDto();
			if(ps.findPlace(placeDto.getId()) == null) {
				ps.createPlace(placeDto);
			}
			mps.createMyplace(myplaceDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{myplaceId}")
	ResponseEntity<?> deleteMyplace(@PathVariable Long myplaceId){
		try {
			mps.deleteMyplace(myplaceId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{myplaceId}")
	ResponseEntity<?> updateMyplace(@RequestBody MyplaceDTO myplaceDto){
		try {
			mps.updateMyplace(myplaceDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/merge/{themeMapId}")
	ResponseEntity<?> mergeMyplace(@PathVariable Long themeMapId, @RequestParam Long mythemeMapId){
		try {
			mps.mergeToMyThemeMap(themeMapId, mythemeMapId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//공개여부확인, 내테마지도를 내테마지도로 복사할때
	@PostMapping("/copy/{themeMapId}")
	ResponseEntity<?> copyMyplace(@PathVariable Long themeMapId, @RequestParam Long mythemeMapId){
		try {
			List<MyplaceDTO> placeList = mps.findAllMyplace(themeMapId);
			for(MyplaceDTO place: placeList) {
				ThemeMapDto thememapDto = new ThemeMapDto();
				thememapDto.setId(mythemeMapId);
				MyplaceDTO copyDto = new MyplaceDTO();
				copyDto.setPlaceId(place.getPlaceId());
				copyDto.setThememapId(thememapDto);
				mps.createMyplace(copyDto);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}


