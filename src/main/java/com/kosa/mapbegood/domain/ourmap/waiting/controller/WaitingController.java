package com.kosa.mapbegood.domain.ourmap.waiting.controller;

import java.util.List;

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

import com.kosa.mapbegood.domain.common.response.Response;
import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.service.WaitingService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/waiting")
public class WaitingController {
	@Autowired
	private WaitingService ws;
	@Autowired
	private AuthenticationUtil authenticationUtil;
	
	//그룹 초대에 응답하지 않은 사람 목록 조회
	@GetMapping(value="{groupId}", produces="application/json;charset=UTF-8")
	public List<MemberDTO> findAllWaitingsByGroupId(Authentication authentication, @PathVariable Long groupId) throws FindException{
		Groups group = new Groups();
		group.setId(groupId);
		return ws.findAllWaitingsByGroupId(group);
	}
	
	//그룹장이 그룹에 사람을 초대했을 때 추가
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createWaiting(Authentication authentication, @RequestBody WaitingDTO waitingDto) {
		try {
			ws.createWaiting(waitingDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(new Response(0, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	//그룹초대 메시지에 수락/거절 했을 때 지워지도록 하기
	@DeleteMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteWaiting(Authentication authentication, @RequestBody WaitingDTO waitingDto) {
		try {
			String email = authenticationUtil.getUserEmail(authentication);
//			log.error("email={}",email);
			waitingDto.setMemberEmail(email);
//			log.error("groupId={}, memberEmail={}", waitingDto.getGroupId(), waitingDto.getMemberEmail());
			ws.deleteWaiting(waitingDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(RemoveException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
