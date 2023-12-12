package com.kosa.mapbegood.domain.ourmap.memberGroup.controller;

import java.util.List;

import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.service.MemberGroupService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;

@RestController
@RequestMapping("/groupmember")
public class MemberGroupController {
private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private MemberGroupService mgs;
	@Autowired
	private AuthenticationUtil authenticationUtil;

	@Autowired
	private MemberService memberService;
	
	//그룹의 전체 멤버 찾기
	@GetMapping(value="/{groupId}", produces="application/json;charset=UTF-8")
	public List<MemberGroupDTO> findAllGroupMembersByGroupId(Authentication authentication, @PathVariable Long groupId) throws FindException{
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			memberService.findMember(email);
			return mgs.findAllGroupMembersByGroupId(groupId);
		} catch(Exception e) {
			log.error("그룹 전체 멤버 찾기 인증 실패");
			return null; 
		}
	}
	
	//그룹의 특정 멤버 찾기
//	@GetMapping(value="/member", produces="application/json;charset=UTF-8")
//	public MemberGroupDTO findGroupMemberByMemberEmailAndGroupId(@RequestBody MemberGroupDTO memberGroupDto)//@PathVariable Long groupId, @RequestBody Map<String,String> map)
//			throws FindException{
//		return mgs.findGroupMemberByMemberEmailAndGroupId(memberGroupDto);
//	}
	
	//그룹초대에 수락했을 때만 멤버로 추가되도록
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createMemberGroup(Authentication authentication, @RequestBody MemberGroupDTO memberGroupDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			MemberDTO m = new MemberDTO();
			m.setEmail(email);
			memberGroupDto.setMember(m);
			mgs.createMemberGroup(memberGroupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//멤버장이 그룹에서 나가려는 경우는 프론트에서 막기
	@DeleteMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteMemberGroup(Authentication authentication, @RequestBody MemberGroupDTO memberGroupDto)  {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
			MemberDTO m = new MemberDTO();
			m.setEmail(email);
			memberGroupDto.setMember(m);
			mgs.deleteMemberGroup(memberGroupDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
