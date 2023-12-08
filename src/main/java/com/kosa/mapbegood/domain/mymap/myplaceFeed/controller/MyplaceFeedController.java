package com.kosa.mapbegood.domain.mymap.myplaceFeed.controller;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.service.MyplaceFeedServiceInterface;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myfeed")
public class MyplaceFeedController {

	@Autowired
	private MyplaceFeedServiceInterface service;

	private final AuthenticationUtil authenticationUtil;

	// GET /myfeed/{myplaceId}
	@GetMapping("/{myplaceId}")
	public ResponseEntity<?> find(@PathVariable Long myplaceId) {
		try {
			MyplaceFeedDTO feed = service.findMyFeedById(myplaceId);
			return ResponseEntity.ok(List.of(feed));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾지 못했습니다");
		}
	}

	// POST /myfeed/{myplaceId}
	@PostMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> create(
			Authentication authentication,
			@RequestBody MyplaceFeedDTO feedDto) {

		try {
			Member memberEmail = new Member();
			memberEmail.setEmail(authenticationUtil.getUserEmail(authentication));
			feedDto.setMemberEmail(memberEmail);

			// MyplaceFeed 등록
			service.createMyFeed(feedDto);

			return ResponseEntity.ok("작성완료");
		} catch (AddException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성 실패: " + e.getMessage());
		}
	}

	// PUT /myfeed/{myplaceId}
	@PutMapping(value = "/{myplaceId}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> update(@PathVariable Long myplaceId, @RequestBody MyplaceFeedDTO feedDto) {
		feedDto.setMyplaceId(myplaceId);

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

