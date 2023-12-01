
package com.kosa.mapbegood.domain.ourmap.groups.controller;

import java.util.List;
import java.util.Map;

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
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
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
	public List<GroupsDTO> findAllGroupsByMemberNickname(@RequestBody Map<String,String> map) throws FindException{
		//ㄴRequestBody로 보내야 응답결과를 json으로 반환이 가능함
		//ㄴㄴRequestBody -> "memberEmail":"사용자의 이메일값"이라는 객체가 받아지기 때문에 Map<>으로 받음
		//ㄴㄴㄴ따라서 메서드에 변수를 넣어줄 때 아래와 같이 넣어줘야함
		return gs.findAllGroupsByMemberEmail(map.get("memberEmail"));
	}
	
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createGroup(@RequestBody MemberGroupDTO memberGroupDto) throws AddException{
		try{
			gs.createGroup(memberGroupDto);
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
