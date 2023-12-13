package com.kosa.mapbegood.domain.ourmap.ourplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.mymap.favorite.dto.ThemeMapDto;
import com.kosa.mapbegood.domain.mymap.myplace.dto.MyplaceDTO;
import com.kosa.mapbegood.domain.mymap.myplace.service.MyplaceService;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceWrapperDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.service.OurplaceService;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.service.PlaceService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

@RestController
@RequestMapping("/ourplace")
public class OurplaceController {
	
	@Autowired
	OurplaceService ops;
	
	@Autowired
	MyplaceService mps;	
	
	@Autowired
	PlaceService ps;
	
    @Autowired
    private AuthenticationUtil authenticationUtil;
	
	@GetMapping("/{groupThememapId}")
	public ResponseEntity<?> findAllOurPlace(@PathVariable Long groupThememapId) throws FindException{
		try {
			List<OurplaceDTO> ourplace = ops.findAllOurplace(groupThememapId);
				return ResponseEntity.ok(ourplace);				
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	 
	@GetMapping("/detail/{ourplaceId}")
	public ResponseEntity<?> findOurPlace(@PathVariable Long ourplaceId) throws FindException{	
		try {
			OurplaceDTO ourplace = ops.findOurplace(ourplaceId);
			if(ourplace!=null) {
				return ResponseEntity.ok(ourplace);				
			}else {
				return ResponseEntity.ok(new FindException("못찾음"));
			}
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
	}
	
	@PostMapping("")
	ResponseEntity<?> createOurplace(Authentication authentication, @RequestBody OurplaceWrapperDTO ourplaceWrapperDto){
		try {
			OurplaceDTO ourplaceDto = ourplaceWrapperDto.getOurplaceDto();
			PlaceDTO placeDto = ourplaceWrapperDto.getPlaceDto();
            String email = authenticationUtil.getUserEmail(authentication);
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            ourplaceDto.setMemberEmail(member);
			if(ps.findPlace(placeDto.getId()) == null) {
				ps.createPlace(placeDto);
			}
			ops.createOurplace(ourplaceDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/{ourplaceId}")
	ResponseEntity<?> deleteOurplace(@PathVariable Long ourplaceId){
		try {
			ops.deleteOurplace(ourplaceId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{ourplaceId}")
	ResponseEntity<?> updateOurplace(@RequestBody OurplaceDTO ourplaceDto){
		try {
			ops.updateOurplace(ourplaceDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/merge/{groupThemeMapId}")
	ResponseEntity<?> mergeMyplace(@PathVariable Long groupThemeMapId, @RequestParam Long mythemeMapId){
		try {
			ops.mergeToMyThemeMap(groupThemeMapId, mythemeMapId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/copy/{groupThemeMapId}")
	ResponseEntity<?> copyMyplace(@PathVariable Long groupThemeMapId, @RequestParam Long mythemeMapId){
		try {
			List<OurplaceDTO> placeList = ops.findAllOurplace(groupThemeMapId);
			for(OurplaceDTO place: placeList) {
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
