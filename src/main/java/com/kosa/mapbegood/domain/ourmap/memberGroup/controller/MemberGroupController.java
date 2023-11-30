package com.kosa.mapbegood.domain.ourmap.memberGroup.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.service.MemberGroupService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

@RestController
@RequestMapping("/groupmember")
public class MemberGroupController {
private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private MemberGroupService mgs;
	
	//그룹의 전체 멤버 찾기
	@GetMapping(value="/{groupId}", produces="application/json;charset=UTF-8")
	public List<MemberGroupDTO> findAllGroupMembersByGroupId(@PathVariable Long groupId) throws FindException{
		return mgs.findAllGroupMembersByGroupId(groupId);
	}
	
	//그룹의 특정 멤버 찾기
//	@GetMapping(value="/member", produces="application/json;charset=UTF-8")
//	public MemberGroupDTO findGroupMemberByMemberEmailAndGroupId(@RequestBody MemberGroupDTO memberGroupDto)//@PathVariable Long groupId, @RequestBody Map<String,String> map)
//			throws FindException{
//		return mgs.findGroupMemberByMemberEmailAndGroupId(memberGroupDto);
//	}
	
	//프론트에서 그룹장에게만 보여주기, 프론트) 그룹장이 추가할 멤버 먼저 검색하고 추가할 수 있게 하기
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createMemberGroup(@RequestBody MemberGroupDTO memberGroupDto) throws AddException{
		try {
			mgs.createMemberGroup(memberGroupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//멤버장이 그룹에서 나가려는 경우는 프론트에서 막기
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
