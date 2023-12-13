package com.kosa.mapbegood.domain.mymap.favorite.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
//필요한 import 문 추가
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.Response;
import com.kosa.mapbegood.domain.mymap.favorite.dto.FavoriteDto;
import com.kosa.mapbegood.domain.mymap.favorite.service.FavoriteService;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

 private final FavoriteService favoriteService;

 @Autowired
 public FavoriteController(FavoriteService favoriteService) {
     this.favoriteService = favoriteService;
 }

 
 //requestbody 요청 : 클라이언트-> 서버
 //responsebody 응답: 서버-> 클라이언트
 
 
 @PostMapping("/create/{thememapId}")  //o
 public ResponseEntity<String> createFavorite(
         @PathVariable(required=false) Long thememapId,
         Authentication authentication) {
	 try {
	        if (thememapId == null) {
	            return new ResponseEntity<>("테마맵 ID가 없습니다.", HttpStatus.BAD_REQUEST);
	        }

	        String userEmail = AuthenticationUtil.getUserEmail(authentication);
	        favoriteService.createFavorite(thememapId, userEmail);
	        return new ResponseEntity<>("즐겨찾기 생성 되었습니다.", HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
 }
 	//중복 확인
 @GetMapping("/duplication/{themeMapId}")
 public ResponseEntity<Map<String, Boolean>> checkDuplicateFavorite(
		 Authentication authentication, @PathVariable Long themeMapId) {
     try {
         String email = AuthenticationUtil.getUserEmail(authentication);
         boolean isDuplicate = favoriteService.isThemeMapAlreadyFavorited(email, themeMapId);
         Map<String, Boolean> response = Collections.singletonMap("isDuplicate", isDuplicate);
         return ResponseEntity.ok(response);
     } catch (Exception e) {
         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
     }
 }
 
 /**
  * 즐겨찾기 삭제
  */
 @DeleteMapping("/delete/{themeMapId}")
 public ResponseEntity<String> deleteFavorite(
         @PathVariable Long themeMapId,
         Authentication authentication) {
     try {
         String userEmail = AuthenticationUtil.getUserEmail(authentication);
         favoriteService.deleteFavorite(themeMapId, userEmail);
         return new ResponseEntity<>("즐겨찾기가 삭제되었습니다.", HttpStatus.OK);
     } catch (Exception e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
     }
 }
 
 
 
//리스트 조회
@GetMapping("/list")//o
public ResponseEntity<List<FavoriteDto>> selectFavoriteList(Authentication authentication) {
  try {
      String userEmail = AuthenticationUtil.getUserEmail(authentication);
      List<FavoriteDto> favoriteList = favoriteService.getAllFavoritesForUser(userEmail);
      if (!favoriteList.isEmpty()) {
          return new ResponseEntity<>(favoriteList, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
 }

