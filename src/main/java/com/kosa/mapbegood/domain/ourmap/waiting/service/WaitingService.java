package com.kosa.mapbegood.domain.ourmap.waiting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;
import com.kosa.mapbegood.domain.ourmap.waiting.repository.WaitingRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class WaitingService {
	@Autowired
	private WaitingRepository wr;
	
	/**
	 * WaitingDTO를 Waiting(엔터티)로 변환한다
	 * @param waitingDto
	 * @return Waiting
	 */
	public Waiting waitingDtoToEntity(WaitingDTO waitingDto) {
		Waiting waitingEntity = new Waiting();
		waitingEntity.setId(waitingDto.getId());
		
		//Waiting(엔터티)에 groupId가 Groups(엔터티)타입이기 때문에 Groups로 넣어줌
		Groups groupsEntity = new Groups();
		Long groupId = waitingDto.getGroupId();
		groupsEntity.setId(groupId);
		waitingEntity.setGroupId(groupsEntity);
		
		Member memberEntity = new Member();
		String memberNickname = waitingDto.getMemberNickname();
		memberEntity.setNickname(memberNickname);
		waitingEntity.setMemberNickname(memberEntity);
		return waitingEntity;
	}
	
	/**
	 * 사용자에게 그룹초대 메시지를 보냈을 때 수락대기테이블에 사용자를 추가한다 
	 * @param waitingDto
	 * @throws AddException
	 */
	public void createWaiting(WaitingDTO waitingDto) throws AddException{
		Waiting entity = waitingDtoToEntity(waitingDto);
		wr.save(entity);
	}
	
	/**
	 * 사용자가 그룹초대를 수락 또는 거절하면 수락대기 테이블에서 사용자를 제거한다
	 * @param waitingId
	 * @throws RemoveException
	 */
	public void deleteWaiting(Long waitingId) throws RemoveException{
		wr.deleteById(waitingId);
	}
}
