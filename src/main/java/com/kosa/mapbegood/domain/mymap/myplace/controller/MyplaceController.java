package com.kosa.mapbegood.domain.mymap.myplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.service.MyplaceService;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

@RestController
@RequestMapping("/place")
public class MyplaceController {
	
	@Autowired
	MyplaceService mps;	
	
	@GetMapping("/{myplaceId}") 
	public ResponseEntity<MyplaceDTO> findMyPlace(@PathVariable Long myplaceId) {
		try {
			MyplaceDTO myplace = mps.findMyplace(myplaceId);
			return ResponseEntity.ok(myplace);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/{myplaceId}")
	ResponseEntity<?> createMyplace(@RequestBody MyplaceDTO myplaceDto){
		try {
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
	
	@PostMapping("/merge/{themeMapId}")
	ResponseEntity<?> mergeMyplace(@PathVariable Long themeMapId, @RequestParam Long mythemeMapId){
		try {
			mps.mergeToMyThemeMap(themeMapId, mythemeMapId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/copy/{themeMapId}")
	ResponseEntity<?> copyMyplace(@PathVariable Long themeMapId, @RequestParam Long mythemeMapId){
		try {
			List<MyplaceDTO> placeList = mps.findAllMyplace(themeMapId);
			for(MyplaceDTO myplace: placeList) {
				ThemeMap thememap = new ThemeMap();
				thememap.setId(mythemeMapId);
				myplace.setThememapId(thememap);
				mps.createMyplace(myplace);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}


