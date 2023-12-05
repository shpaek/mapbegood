package com.kosa.mapbegood.domain.mymap.myplaceFeed.controller;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.service.MyplaceFeedServiceInterface;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myfeed")
public class MyplaceFeedController {

	@Autowired
	private MyplaceFeedServiceInterface service;

	@Autowired
	private AuthenticationUtil authenticationUtil;

	//GET	/myfeed/{myplaceId}
	@GetMapping("/{myplaceId}")
	public MyplaceFeed find(@PathVariable Long myplaceId) throws FindException{
		return service.findMyFeedById(myplaceId);
	}

	//POST	/myfeed/{myplaceId}
	@PostMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> create(Authentication authentication, @RequestBody MyplaceFeedDTO feedDto) throws AddException {
		try {
			Member memberEmail = new Member();
			memberEmail.setEmail(authenticationUtil.getUserEmail(authentication));
			feedDto.setMemberEmail(memberEmail);
			service.createMyFeed(feedDto);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html;charset=UTF-8");
			return new ResponseEntity<>("작성완료",
					headers,
					HttpStatus.OK);
		} catch (AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	//PUT	/myfeed/{myplaceId}
	@PutMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> update(@PathVariable Long myplaceId, @RequestBody MyplaceFeedDTO feedDto) throws ModifyException, FindException {
		feedDto.setMyplaceId(myplaceId);

		service.updateMyFeed(feedDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{myplaceId}")
	public ResponseEntity<?> delete(@PathVariable Long myplaceId) throws RemoveException, FindException {
		service.deleteMyFeed(myplaceId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
