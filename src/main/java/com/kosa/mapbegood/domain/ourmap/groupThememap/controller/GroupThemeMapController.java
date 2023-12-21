package com.kosa.mapbegood.domain.ourmap.groupThememap.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.mymap.thememap.controller.ThemeMapController;
import com.kosa.mapbegood.domain.ourmap.groupThememap.dto.GroupThememapDTO;
import com.kosa.mapbegood.domain.ourmap.groupThememap.service.GroupThemeMapService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ourmap")
public class GroupThemeMapController {


	 
	private GroupThemeMapService groupthememapservice;  
	
	@Autowired
    private AuthenticationUtil authenticationUtil;
	
	 @Autowired
	    public GroupThemeMapController(GroupThemeMapService groupThemeMapService) {
	        this.groupthememapservice = groupThemeMapService;
	    }
	//그룹 테마맵 생성
	// 그룹 테마맵 생성 o
	    @PostMapping("/create/{groupId}")
	    public ResponseEntity<GroupThememapDTO> createGroupThememap(@PathVariable Long groupId, @RequestBody GroupThememapDTO groupThememapDTO) {
	        try {
 	            GroupThememapDTO createdGroupThememap = groupthememapservice.createGroupThememap(groupId, groupThememapDTO);

	            return new ResponseEntity<>(createdGroupThememap, HttpStatus.CREATED);
	        } catch (FindException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    // 그룹 테마맵 삭제 o
	    @DeleteMapping("/delete/{groupId}/{groupThememapId}")
	    public ResponseEntity<Void> deleteGroupThemeMap(@PathVariable Long groupId,
	            @PathVariable Long groupThememapId) {
	        try {
	            groupthememapservice.deleteGroupThememap(groupId, groupThememapId);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (FindException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	 }
	    //그룹 테마맵 수정 o 
	    @PutMapping("/update/{groupId}/{groupThememapId}")
	    public ResponseEntity<GroupThememapDTO> updateGroupThemeMap(@PathVariable Long groupId,
	            @PathVariable Long groupThememapId, @RequestBody GroupThememapDTO groupthemeMapDto) {
	        try {
	            GroupThememapDTO updatedGroupThememap = groupthememapservice.updateGroupThememap(groupId, groupThememapId, groupthemeMapDto);
	            return new ResponseEntity<>(updatedGroupThememap, HttpStatus.OK);
	        } catch (FindException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	  //그룹 테마맵 조회 o 
	    @GetMapping("/getall/{groupId}")
	    public ResponseEntity<List<GroupThememapDTO>> getAllGroupThememaps(@PathVariable Long groupId) {
	    	 try {
	    	        List<GroupThememapDTO> groupThememaps = groupthememapservice.getAllGroupThememaps(groupId);
	    	        return new ResponseEntity<>(groupThememaps, HttpStatus.OK);
	    	    } catch (FindException e) {
//	    	    	e.printStackTrace();
	    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	    }	    	
	    }
	    
	    @GetMapping("/get/{groupThememapId}")
	    public ResponseEntity<GroupThememapDTO> getGroupThememapDetail(
	             
	            @PathVariable Long groupThememapId
	    ) {
	        try {
	            GroupThememapDTO groupThememap = groupthememapservice.getGroupThememapDetail(groupThememapId);
	            System.out.println(groupThememap.getColor());
	            return new ResponseEntity<>(groupThememap, HttpStatus.OK);
	        } catch (FindException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	
}		
	
	
	
