package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeed;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.mapper.OurplaceFeedMapper;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.repository.OurplaceFeedRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OurplaceFeedService implements OurplaceFeedServiceInterface {
    @Autowired
    private OurplaceFeedRepository ofr;

    @Autowired
    private OurplaceFeedMapper mapper;

    /**
     * DTO 객체 타입의 그룹피드id(아워플레이스id, 닉네임)를 entity 타입으로 변환한다
     * @param feedDto 피드
     * @return 복합키 엔티티
     */
    public OurplaceFeedEmbedded getOurFeedEmId(OurplaceFeedDTO feedDto) {
        return mapper.ourplaceFeedDTOtoOurplaceFeedEmbedded(feedDto);
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
        for (OurplaceFeed of : ofList) {
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

        if (feed.isPresent()) {
            return mapper.OurplaceFeedToOurplaceFeedDTO(feed.get());
        } else {
            throw new FindException("해당 피드를 찾을 수 없습니다");
        }
    }

    /**
     * 내 그룹피드를 생성할 수 있다
     * @param feedDto 그룹피드
     * @throws AddException
     */
    public void createOurFeed(OurplaceFeedDTO feedDto) throws AddException {
        OurplaceFeed of = mapper.OurplaceFeedDTOtoOurplaceFeed(feedDto);
        try {
            ofr.save(of);
        } catch (DataAccessException e) {
            throw new AddException("피드 생성 중 오류가 발생했습니다");
        }
    }

    /**
     * 내 그룹피드를 수정할 수 있다
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
     * 내 그룹피드를 삭제할 수 있다
     * @param feedId 피드id(아워플레이스id, 닉네임)
     * @throws RemoveException
     */
    public void deleteOurFeed(OurplaceFeedEmbedded feedId) throws RemoveException, FindException {
        Optional<OurplaceFeed> feed = ofr.findById(feedId);
        if (feed.isPresent()) {
            try{
                ofr.deleteById(feedId);
            } catch(EmptyResultDataAccessException e){
                throw new RemoveException("피드 삭제 중 오류가 발생했습니다");
            }
        } else {
            throw new FindException("해당 피드를 찾을 수 없습니다");
        }
    }

}
