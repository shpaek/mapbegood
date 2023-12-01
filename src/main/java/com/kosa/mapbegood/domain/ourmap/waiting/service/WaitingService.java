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
		
		//엔터티 연결을 양방향->단방향을 바꿨기 때문에 코드수정함
		//위가 단방향일때 아래가 양방향일때
		//Waiting(엔터티)에 groupId가 Groups(엔터티)타입이기 때문에 Groups로 넣어줌
//		Groups groupsEntity = new Groups();
//		Long groupId = waitingDto.getGroupId();
//		groupsEntity.setId(groupId);
//		waitingEntity.setGroupId(groupsEntity);
		
//		Member memberEntity = new Member();
//		String memberNickname = waitingDto.getMemberNickname();
//		memberEntity.setNickname(memberNickname);
//		waitingEntity.setMemberNickname(memberEntity);
		return waitingEntity;
	}
	
	/**
	 * 특정 그룹의 수락대기목록(명단)을 조회한다
	 * @param groupId
	 * @return 특정 그룹의 수락대기중인 사용자의 닉네임 
	 * @throws FindException
	 */
	public List<MemberDTO> findAllWaitingsByGroupId(Long groupId) throws FindException{
		List<MemberDTO> resultMemberDtoList = new ArrayList();
		try {
			List<Waiting> waitingList = wr.findByGroupId(groupId);
			for(int i=0;i<waitingList.size();i++) {
				Waiting waiting = waitingList.get(i); //수락대기 1개로 닉네임 찾기
				Optional<Member> optMember = mr.findById(waiting.getMemberEmail());
				if(optMember.isPresent()) {
					Member member = optMember.get();
					MemberDTO memberDto = new MemberDTO();
					memberDto.setNickname(member.getNickname());
					resultMemberDtoList.add(memberDto);
				}
			}			
			return resultMemberDtoList;
		}catch(Exception e) {
			new FindException(e.getMessage());
			return null;
		}
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
	 * WaitingDTO로 Waiting이 있는지 조회하고 id값을 받아온다
	 * @param waitingDto
	 * @return Waiting(id값)
	 * @throws FindException
	 */
	public Waiting findIdByGroupIdAndMemberEmail(WaitingDTO waitingDto) throws FindException{
		Waiting waiting = new Waiting();
		waiting = waitingDtoToEntity(waitingDto);
		try {			
			waiting = wr.findByGroupIdAndMemberEmail(waiting.getGroupId(), waiting.getMemberEmail()); //waiting id받아옴
			return waiting;
		}catch(Exception e) {
			new FindException(e.getMessage());
			return null;
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
			wr.deleteById(waiting.getId());
		} catch (Exception e) {
			new RemoveException("수락대기 삭제 실패");
		}
	}
}
