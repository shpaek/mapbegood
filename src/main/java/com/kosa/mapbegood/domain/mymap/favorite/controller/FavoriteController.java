//package com.kosa.mapbegood.domain.mymap.favorite.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
////필요한 import 문 추가
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.kosa.mapbegood.domain.mymap.favorite.dto.FavoriteDto;
//import com.kosa.mapbegood.domain.mymap.favorite.service.FavoriteService;
//
//@RestController
//@RequestMapping("/favoritemap")
//public class FavoriteController {
//
// private final FavoriteService favoriteService;
//
// @Autowired
// public FavoriteController(FavoriteService favoriteService) {
//     this.favoriteService = favoriteService;
// }
//
// @PostMapping
// public ResponseEntity<String> createFavorite(@RequestBody FavoriteDto favoriteDto) {
//     try {
//         favoriteService.createFavorite(favoriteDto);
//         return new ResponseEntity<>("생성댓다", HttpStatus.CREATED);
//     } catch (Exception e) {
//         return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//     }
// }
// @GetMapping("/{nickname}")
// public ResponseEntity<List<FavoriteDto>> selectFavoriteList(@PathVariable String nickname) {
//     
//	 List<FavoriteDto> favoriteList = favoriteService.selectFavoriteList(nickname);
//     if (!favoriteList.isEmpty()) {
//    	 
//         return new ResponseEntity<>(favoriteList, HttpStatus.OK);
//     } else {
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }
// }
//}
