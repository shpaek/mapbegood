package com.kosa.mapbegood.domain.ourmap.waiting.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.ourmap.waiting.dto.WaitingDTO;
import com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting;
import com.kosa.mapbegood.domain.ourmap.waiting.repository.WaitingRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.RemoveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WaitingService {
	@Autowired
	private WaitingRepository wr;
	@Autowired
	private MemberRepository mr;
	
	/**
	 * WaitingDTO를 Waiting(엔터티)로 변환한다
	 * @param waitingDto
	 * @return Waiting
	 */
	public Waiting waitingDtoToEntity(WaitingDTO waitingDto) {
		Waiting waitingEntity = new Waiting();
//		waitingEntity.setId(waitingDto.getId());
		waitingEntity.setGroupId(waitingDto.getGroupId());
		waitingEntity.setMemberEmail(waitingDto.getMemberEmail());
		return waitingEntity;
	}
	
	/**
	 * 특정 그룹의 수락대기목록(명단)을 조회한다
	 * @param groupId
	 * @return 특정 그룹의 수락대기중인 사용자의 닉네임과 이메일
	 * @throws FindException
	 */
	public List<MemberDTO> findAllWaitingsByGroupId(Long groupId) throws FindException{
		List<MemberDTO> resultMemberDtoList = new ArrayList();
		try {
			List<Waiting> waitingList = wr.findByGroupId(groupId);
			log.error("waitingList={}",waitingList);
			if(waitingList==null) {
				throw new FindException("수락대기 명단이 없습니다");
			}
			for(int i=0;i<waitingList.size();i++) {
				Waiting waiting = waitingList.get(i); //수락대기 1개로 닉네임 찾기
				Optional<Member> optMember = mr.findById(waiting.getMemberEmail());
				if(optMember.isPresent()) {
					Member member = optMember.get();
					MemberDTO memberDto = new MemberDTO();
					memberDto.setEmail(member.getEmail());
					memberDto.setNickname(member.getNickname());
					resultMemberDtoList.add(memberDto);
				}
			}			
			return resultMemberDtoList;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}
	}
	
	
	
	/**
	 * 사용자에게 그룹초대 메시지를 보냈을 때 수락대기테이블에 사용자를 추가한다 
	 * @param waitingDto
	 * @throws AddException
	 * @throws FindException 
	 */
	public void createWaiting(WaitingDTO waitingDto) throws AddException{
		try {
			Waiting waiting = findIdByGroupIdAndMemberEmail(waitingDto);
//			log.error("waiting={}", waiting.getId());
			if(waiting != null) {
				throw new AddException("이미 초대를 요청했습니다");				
			}
		} catch (FindException fe) {
			Waiting entity = waitingDtoToEntity(waitingDto);
			wr.save(entity);				
		}
	}
	
	
	/**
	 * WaitingDTO로 Waiting이 있는지 조회하고 id값을 받아온다
	 * 
	 * @param waitingDto
	 * @return Waiting(id값)
	 * @throws FindException
	 */
	public Waiting findIdByGroupIdAndMemberEmail(WaitingDTO waitingDto) throws FindException {
		Waiting waiting = new Waiting();
		waiting = waitingDtoToEntity(waitingDto);
		Optional<Waiting> optWaiting = wr.findByGroupIdAndMemberEmail(waiting.getGroupId(), waiting.getMemberEmail()); // waiting																											// id받아옴
		if (optWaiting.isPresent()) {
			waiting = optWaiting.get();
			return waiting;
		} else {
			throw new FindException("수락대기 목록이 없습니다");
		}

	}

	/**
	 * 사용자가 그룹초대를 수락 또는 거절하면 수락대기 테이블에서 사용자를 제거한다
	 * @param waitingId
	 * @throws RemoveException
	 */
	public void deleteWaiting(WaitingDTO waitingDto) throws RemoveException{
		try {
			Waiting waiting = findIdByGroupIdAndMemberEmail(waitingDto);
//			log.error("delete단 waiting.getId={}", waiting.getId());
			wr.deleteById(waiting.getId());
		} catch (Exception e) {
			throw new RemoveException("수락대기 삭제 실패"+e.getMessage());
		}
	}
}
