package com.kosa.mapbegood.domain.ourmap.memberGroup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.service.MemberGroupService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.RemoveException;

@RestController
@RequestMapping("/groupmember")
public class MemberGroupController {
private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private MemberGroupService mgs;
	
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createMemberGroup(@RequestBody MemberGroupDTO memberGroupDto) throws AddException{
		try {
			mgs.createMemberGroup(memberGroupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//멤버장이 그룹에서 나가려는 경우 Service에서 막음
	@DeleteMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteMemberGroup(@RequestBody MemberGroupDTO memberGroupDto) throws RemoveException {
		try {
			mgs.deleteMemberGroup(memberGroupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
