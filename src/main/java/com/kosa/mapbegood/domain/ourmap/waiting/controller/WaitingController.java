package com.kosa.mapbegood.domain.ourmap.waiting.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.service.WaitingService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/waiting")
public class WaitingController {
	@Autowired
	private WaitingService ws;
	
	@GetMapping(value="", produces="application/json;charset=UTF-8")
	public List<MemberDTO> findAllWaitingsByGroupId(@PathVariable Long groupId) throws FindException{
		return ws.findAllWaitingsByGroupId(groupId);
	}
	
	
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createWaiting(@RequestBody WaitingDTO waitingDto) throws AddException{
		try {
			ws.createWaiting(waitingDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteWaiting(@RequestBody WaitingDTO waitingDto) throws RemoveException{
		try {
			ws.deleteWaiting(waitingDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(RemoveException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
