package com.kosa.mapbegood.domain.mymap.thememap.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap;
import com.kosa.mapbegood.domain.ourmap.groupThememap.repository.GroupThemeMapRepository;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
public class GroupThemeMapRepositoryTest {

    @Autowired
    private com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository grepository;
    
    @Autowired
    private GroupThemeMapRepository repository;

    @Test
    public void testSelectGroupThemeMapById() {
        // 조회할 GroupThememap의 ID
        long groupThemeMapId = 1;
        
        // optional은 값이 null이 될 수 있는 객체를 감싸는 래퍼 클래스임.
        Optional<GroupThememap> optionalGroupThemeMap = repository.findById(groupThemeMapId);
        
        // 테마 맵이 존재하는 경우
        if (optionalGroupThemeMap.isPresent()) {
            GroupThememap selectedGroupThemeMap = optionalGroupThemeMap.get();
            log.info("조회했습니다. - {}", selectedGroupThemeMap);
        } else {
            log.info("존재하지 않습니다.");
        }
    }

     
    @Test
    public void testInsertGroupThemeMap() {
        GroupThememap groupThemeMap = new GroupThememap();
        Groups group = new Groups();
        // Groups 엔티티 생성 및 저장
        group.setId(1L);
        group.setName("jt");
//        group.setLeaderNickname("test");
        grepository.save(group);

        // GroupThememap 엔티티 생성
         
        groupThemeMap.setId(1L);
        groupThemeMap.setMemo("dd");
        groupThemeMap.setColor("black"); 
        groupThemeMap.setName("Test Group ThemeMap");

        // GroupThememap 저장
        repository.save(groupThemeMap);
        log.info("그룹 테마 맵이 추가되었습니다. - {}", groupThemeMap);
    }

    @Test
    public void testUpdateGroupThemeMap() {
        // 업데이트할 GroupThememap의 ID
        long groupThemeMapId = 1;

        // optional은 값이 null이 될 수 있는 객체를 감싸는 래퍼 클래스임.
        Optional<GroupThememap> optionalGroupThemeMap = repository.findById(groupThemeMapId);

        if (optionalGroupThemeMap.isPresent()) {
            GroupThememap updateGroupThemeMap = optionalGroupThemeMap.get();
            updateGroupThemeMap.setName("Updated Group ThemeMap");
            repository.save(updateGroupThemeMap);
            log.info("그룹 테마 맵이 업데이트되었습니다. - {}", updateGroupThemeMap);
        } else {
            log.info("존재하지 않는 그룹 테마 맵입니다.");
        }
    }

    @Test
    public void testDeleteGroupThemeMap() {
        // 삭제할 GroupThememap의 ID
        long groupThemeMapId = 1;

        // optional은 값이 null이 될 수 있는 객체를 감싸는 래퍼 클래스임.
        Optional<GroupThememap> optionalGroupThemeMap = repository.findById(groupThemeMapId);

        if (optionalGroupThemeMap.isPresent()) {
            GroupThememap deleteGroupThemeMap = optionalGroupThemeMap.get();
            
            // 그룹 테마 맵 삭제
            repository.delete(deleteGroupThemeMap);
            log.info("그룹 테마 맵이 삭제되었습니다. - {}", deleteGroupThemeMap);
        } else {
            log.info("존재하지 않는 그룹 테마 맵입니다.");
        }
    }
}