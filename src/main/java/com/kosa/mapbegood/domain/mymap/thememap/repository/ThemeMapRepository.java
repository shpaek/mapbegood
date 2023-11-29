package com.kosa.mapbegood.domain.mymap.thememap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

public interface ThemeMapRepository extends JpaRepository<ThemeMap, Long> {

   public List<ThemeMap> findByMemberEmail(Member m);

    
}