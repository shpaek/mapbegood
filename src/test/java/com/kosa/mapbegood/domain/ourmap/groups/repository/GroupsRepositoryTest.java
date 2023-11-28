package com.kosa.mapbegood.domain.ourmap.groups.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class GroupsRepositoryTest {
	@Autowired
	private GroupsRepository gr;

	@Test
	@DisplayName("그룹 생성")
	public void createGroup() {
		Groups entity = new Groups();
		entity.setId(103L);
		entity.setName("groupNameTest");
		entity.setLeaderNickname("dlto");

		gr.save(entity);
	}
}
