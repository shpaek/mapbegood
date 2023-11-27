package com.kosa.mapbegood.domain.mymap.thememap.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

public interface ThemeMapRepository extends JpaRepository<ThemeMap, Long> {

   

    
}