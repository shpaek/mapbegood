package com.kosa.mapbegood.domain.mymap.thememap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.thememap.entity.ThemeMap;

public interface ThemeMapRepository extends JpaRepository<ThemeMap, Long>, ThemeMapCustomRepository {

   public List<ThemeMap> findByMemberEmail(Member m);

   //이름 설정과 show가 true라고 설정될때 
   List<ThemeMap>findByNameContainingAndShowTrue(String name); 
//   public List<ThemeMap>findByShowTrue(ThemeMap t);
   
   //현중
   boolean findShowById(Long id);

public Optional<ThemeMap> findByIdAndMemberEmail(Long themeMapId, Member findMember);
    
   
}
