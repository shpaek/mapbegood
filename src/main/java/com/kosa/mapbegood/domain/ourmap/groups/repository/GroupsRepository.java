package com.kosa.mapbegood.domain.ourmap.groups.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
public interface GroupsRepository extends JpaRepository<Groups, Long>{

//	@Query(nativeQuery=true,
//		   value="SELECT g.*\r\n"
//		   		+ "FROM groups g JOIN member_group mg ON (g.id=mg.group_id)\r\n"
//		   		+ "WHERE mg.member_email=:memberEmail")
//	public List<Groups> findMembersAllGroup(String memberEmail);

}
