package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.dto.MemberSignUpDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;
import com.kosa.mapbegood.domain.ourmap.ourplace.repository.OurplaceRepository;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.mapper.OurplaceFeedMapper;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.repository.OurplaceFeedRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class OurplaceFeedService {
	@Autowired
	private OurplaceFeedRepository ofr;

	@Autowired
	private OurplaceRepository opr;

	@Autowired
	private MemberRepository mr;

	@Autowired
	private OurplaceFeedMapper mapper;

	/**
	 * DTO 객체 타입의 그룹피드id(아워플레이스id, 이메일)를 entity 타입으로 변환한다
	 * @param feedDto 피드
	 * @return 복합키 엔티티
	 */
	public OurplaceFeedEmbedded getOurFeedEmId(OurplaceFeedDTO feedDto) {
		OurplaceFeedEmbedded emid = new OurplaceFeedEmbedded();

		emid.setOurplaceId(feedDto.getOurplaceId().getId());
		emid.setEmail(feedDto.getMemberEmail().getEmail());
		return emid;
	}

	/**
	 * 그룹피드를 조회할 수 있다
	 * @param feedId 그룹피드id(아워플레이스id, 이메일)
	 * @return 그룹피드
	 * @throws FindException
	 */
	public OurplaceFeedDTO findOurFeedById(OurplaceFeedEmbedded feedId) throws FindException {
		Optional<OurplaceFeed> feed = ofr.findById(feedId);
		OurplaceFeedDTO ourfeed = new OurplaceFeedDTO();
		ourfeed = mapper.entityToDto(feed.get());
		Member member = feed.get().getMember();
		MemberDTO memberDto = new MemberDTO();
		memberDto.setEmail(member.getEmail());
		memberDto.setNickname(member.getNickname());
		memberDto.setProfileImage(member.getProfileImage());
		ourfeed.setMemberEmail(memberDto);
		if (feed.isPresent()) {
			return ourfeed;
		} else {
			return null;
		}
	}

	/**
	 * 그룹피드를 생성할 수 있다
	 * @param feedDto 그룹피드
	 * @throws AddException
	 */
	public void createOurFeed(OurplaceFeedDTO feedDto) throws AddException {
		Optional<Member> m = mr.findByEmail(feedDto.getMemberEmail().getEmail());
		Optional<Ourplace> op = opr.findById(feedDto.getOurplaceId().getId());
		Member member = m.get();
		Ourplace ourplace = op.get();
		try {
			OurplaceFeed of = OurplaceFeed.builder()
					.id(getOurFeedEmId(feedDto))
					.member(member)
					.ourplace(ourplace)
					.groupId(feedDto.getGroupId())
					.content(feedDto.getContent())
					.build();

			ofr.save(of);
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new AddException("피드 생성 중 오류가 발생했습니다");
		}

	}

	/**
	 * 그룹피드를 수정할 수 있다
	 * @param feedDto 피드
	 * @throws FindException
	 * @throws AddException
	 */
	public void updateOurFeed(OurplaceFeedDTO feedDto) throws FindException, ModifyException {
		Optional<OurplaceFeed> feed = ofr.findById(getOurFeedEmId(feedDto));
		if(feed.isPresent()) {
			OurplaceFeed updatedFeed = OurplaceFeed
					.builder()
					.id(feed.get().getId())
					.groupId(feed.get().getGroupId())
					.content(feedDto.getContent())
					.build();
			try {
				ofr.save(updatedFeed);
			} catch (DataAccessException e) {
				throw new ModifyException("피드 수정 중 오류가 발생했습니다");
			}
		}else{
			throw new FindException("해당 피드를 찾을 수 없습니다");
		}
	}

	/**
	 * 그룹피드를 삭제할 수 있다
	 * @param feedId 피드id(아워플레이스id, 이메일)
	 * @throws RemoveException
	 */
	public void deleteOurFeed(OurplaceFeedEmbedded feedId) throws RemoveException {
		Optional<OurplaceFeed> feed = ofr.findById(feedId);
		if (feed.isPresent()) {
			OurplaceFeed ourfeed = feed.get();
			ofr.delete(ourfeed);
		} else {
			throw new RemoveException("해당 피드를 삭제할 수 없습니다");
		}
	}

}