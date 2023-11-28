package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.repository.OurplaceFeedRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

@Service
public class OurplaceFeedService {
    @Autowired
    private OurplaceFeedRepository ofr;

    /**
     * DTO 객체 타입의 그룹피드id(아워플레이스id, 닉네임)를 entity 타입으로 변환한다
     * @param feedDto 피드
     * @return 복합키 엔티티
     */
    public OurplaceFeedEmbedded getOurFeedEmId(OurplaceFeedDTO feedDto) {
        OurplaceFeedEmbedded ofid = new OurplaceFeedEmbedded();
        ofid.setOurplaceId(feedDto.getOurplaceId());
//        ofid.setNickname(feedDto.getMemberNickname());
        return ofid;
    }

    /**
     * 특정 아워플레이스의 전체그룹피드를 조회할 수 있다
     * @param ourplaceId 아워플레이스id
     * @return 그룹피드목록
     * @throws FindException
     */
    public List<OurplaceFeedDTO> findAllOurFeed(Iterable<OurplaceFeedEmbedded> ourplaceId) throws FindException {
        List<OurplaceFeed> ofList = ofr.findAllById(ourplaceId);
        List<OurplaceFeedDTO> ourFeedList = new ArrayList<>();
        for(OurplaceFeed of: ofList){
            OurplaceFeedDTO ourFeedDto = findOurFeedById(of.getId());
            ourFeedList.add(ourFeedDto);
        }
        return ourFeedList;
    }

    /**
     * 내 그룹피드를 조회할 수 있다
     * @param feedId 그룹피드id(아워플레이스id, 닉네임)
     * @return 그룹피드
     * @throws FindException
     */
    public OurplaceFeedDTO findOurFeedById(OurplaceFeedEmbedded feedId) throws FindException {
        Optional<OurplaceFeed> feed = ofr.findById(feedId);
        OurplaceFeedDTO feedDto = new OurplaceFeedDTO();
        feedDto.setOurplaceId(feedId.getOurplaceId());
        feedDto.setMemberEmail(feedId.getEmail());
        feedDto.setContent(feed.get().getContent());
        feedDto.setCreatedAt(feed.get().getCreatedAt());
        return feedDto;
    }

    /**
     * 내 그룹피드를 생성할 수 있다
     * @param feedDto 그룹피드
     * @throws AddException
     */
    public void createOurFeed(OurplaceFeedDTO feedDto) throws AddException {
        OurplaceFeed of = OurplaceFeed
                .builder()
                .id(getOurFeedEmId(feedDto))
                .content(feedDto.getContent())
                .build();
        ofr.save(of);

    }

    /**
     * 내 그룹피드를 수정할 수 있다
     * @param feedDto 피드
     * @throws FindException
     * @throws AddException
     */
    public void updateOurFeed(OurplaceFeedDTO feedDto) throws FindException, ModifyException {
        OurplaceFeedDTO ofDto = findOurFeedById(getOurFeedEmId(feedDto));

        OurplaceFeed of = OurplaceFeed
                .builder()
                .id(getOurFeedEmId(ofDto))
                .content(feedDto.getContent())
                .build();

        ofr.save(of);
    }

    /**
     * 내 그룹피드를 삭제할 수 있다
     * @param feedId 피드id(아워플레이스id, 닉네임)
     * @throws RemoveException
     */
    public void deleteOurFeed(OurplaceFeedEmbedded feedId) throws RemoveException {
        ofr.deleteById(feedId);
    }

}
