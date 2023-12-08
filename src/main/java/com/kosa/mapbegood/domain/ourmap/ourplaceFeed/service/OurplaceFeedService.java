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

import java.util.Optional;

@Service
public class OurplaceFeedService implements OurplaceFeedServiceInterface {
    @Autowired
    private OurplaceFeedRepository ofr;

    @Autowired
    private OurplaceFeedMapper mapper;

    public OurplaceFeedEmbedded getOurFeedEmId(OurplaceFeedDTO feedDto) {
        return mapper.ourplaceFeedDTOtoOurplaceFeedEmbedded(feedDto);
    }

    public OurplaceFeedDTO findOurFeedById(OurplaceFeedEmbedded feedId) throws FindException {
        Optional<OurplaceFeed> feed = ofr.findById(feedId);

        if (feed.isPresent()) {
            return mapper.OurplaceFeedToOurplaceFeedDTO(feed.get());
        } else {
            throw new FindException("해당 피드를 찾을 수 없습니다");
        }
    }

    public void createOurFeed(OurplaceFeedDTO feedDto) throws AddException {
        OurplaceFeed of = mapper.OurplaceFeedDTOtoOurplaceFeed(feedDto);
        try {
            ofr.save(of);
        } catch (DataAccessException e) {
            throw new AddException("피드 생성 중 오류가 발생했습니다");
        }
    }

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
