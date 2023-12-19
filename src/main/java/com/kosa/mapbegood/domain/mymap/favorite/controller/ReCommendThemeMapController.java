package com.kosa.mapbegood.domain.mymap.favorite.controller;

import com.kosa.mapbegood.domain.mymap.favorite.dto.RecommendThemeMapDto;
import com.kosa.mapbegood.domain.mymap.favorite.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReCommendThemeMapController {
    private final FavoriteRepository repository;

    @GetMapping(value = {"/recommend-thememap", "/recommend-thememap/{pageNum}"})
    public List<RecommendThemeMapDto> findReCommendThemeMap(@PathVariable(required = false) Optional<Integer> pageNum) {
        int num = pageNum.orElse(1);
        return repository.findReCommendThemeMap(num);
    }
}
