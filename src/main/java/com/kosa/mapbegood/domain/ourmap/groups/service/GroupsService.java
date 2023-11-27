package com.kosa.mapbegood.domain.ourmap.groups.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;

@Service
public class GroupsService {
	@Autowired
	private GroupsRepository gr;
	
	//vo->dto
	//dto->vo
	//내그룹 목록조회
	//그룹생성(누구나)
	//그룹수정(그룹장)
	//그룹삭제(그룹장)
	
}
