package com.kosa.mapbegood.domain.mymap.myplaceFeed.controller;

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

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.service.MyplaceFeedService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myfeed")
public class MyplaceFeedController {

	@Autowired
	private MyplaceFeedService service;

	private final AuthenticationUtil authenticationUtil;

	// GET /myfeed/{myplaceId}
	@GetMapping("/{myplaceId}")
	public ResponseEntity<?> find(@PathVariable Long myplaceId) throws FindException {
		try {
			MyplaceFeedDTO feed = service.findMyFeedById(myplaceId);
			if(feed!=null) {
				return ResponseEntity.ok(feed);				
			}else {
				return ResponseEntity.ok(new FindException("피드를 찾을 수 없습니다"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾을 수 없습니다");
		}
	}

	// POST /myfeed/{myplaceId}
	@PostMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> create(
			Authentication authentication,
			@RequestBody MyplaceFeedDTO feedDto) {

		try {
			String email = authenticationUtil.getUserEmail(authentication);
			MemberDTO member = new MemberDTO();
			member.setEmail(email);
			feedDto.setMemberEmail(member);

			service.createMyFeed(feedDto);

			return ResponseEntity.ok("작성완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성 실패: " + e.getMessage());
		}
	}

	// PUT /myfeed/{myplaceId}
	@PutMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> update(@RequestBody MyplaceFeedDTO feedDto) {
		feedDto.setMyplaceId(feedDto.getMyplaceId());

		try {
			service.updateMyFeed(feedDto);
			return ResponseEntity.ok("수정 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패: " + e.getMessage());
		}
	}

	// DELETE /myfeed/{myplaceId}
	@DeleteMapping("/{myplaceId}")
	public ResponseEntity<?> delete(@PathVariable Long myplaceId) {
		try {
			service.deleteMyFeed(myplaceId);
			return ResponseEntity.ok("삭제 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패: " + e.getMessage());
		}
	}
}
