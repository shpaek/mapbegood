package com.kosa.mapbegood.domain.ourmap.groups.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.service.GroupsService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

@RestController
@RequestMapping("/group")
public class GroupsController {
	private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private GroupsService gs;
	
	@GetMapping(value="", produces="application/json;charset=UTF-8")
	public List<GroupsDTO> findAllGroupsByMemberNickname(String memberNickname) throws FindException{
		return gs.findMembersAllGroup(memberNickname);
	}
	
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createGroup(@RequestBody GroupsDTO groupsDto) throws AddException{
		try{
			gs.createGroup(groupsDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/{id}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupsDTO groupsDto) throws ModifyException{
		groupsDto.setId(id);
		try {
			gs.updateGroup(groupsDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(ModifyException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="/{id}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) throws RemoveException {
		try{
			gs.deleteGroup(id);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
