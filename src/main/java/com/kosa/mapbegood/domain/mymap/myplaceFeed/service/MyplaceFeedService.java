package com.kosa.mapbegood.domain.mymap.myplaceFeed.service;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.mapper.MyplaceFeedMapper;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.repository.MyplaceFeedRepository;
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
public class MyplaceFeedService implements MyplaceFeedServiceInterface {

    @Autowired
    private MyplaceFeedRepository mfr;

    @Autowired
    private MyplaceFeedMapper mapper;

    public MyplaceFeedDTO findMyFeedById(Long myplaceId) throws FindException {
            Optional<MyplaceFeed> feed = mfr.findById(myplaceId);
            if (feed.isPresent()) {
                return mapper.MyplaceToMyplaceFeedDTO(feed.get());
            } else {
                throw new FindException("해당 피드를 찾을 수 없습니다");
            }
    }

    public void createMyFeed(MyplaceFeedDTO feedDto) throws AddException {
        MyplaceFeed mf = mapper.MyplaceFeedDTOtoMyplaceFeed(feedDto);
        try {
            mfr.save(mf);
        } catch (DataAccessException e) {
            throw new AddException("피드 생성 중 오류가 발생했습니다");
        }
    }

    public void updateMyFeed(MyplaceFeedDTO feedDto) throws FindException, ModifyException {
        Optional<MyplaceFeed> feed = mfr.findById(feedDto.getMyplaceId());
        if (feed.isPresent()) {
            MyplaceFeed updatedFeed = MyplaceFeed.builder()
                    .myplaceId(feed.get().getMyplaceId())
                    .memberEmail(feed.get().getMemberEmail())
                    .content(feedDto.getContent())
                    .build();
            try {
                mfr.save(updatedFeed);
            } catch (DataAccessException e) {
                throw new ModifyException("피드 수정 중 오류가 발생했습니다");
            }
        } else {
            throw new FindException("해당 피드를 찾을 수 없습니다");
        }
    }

    public void deleteMyFeed(Long feedId) throws RemoveException, FindException {
        Optional<MyplaceFeed> feed = mfr.findById(feedId);
        if (feed.isPresent()) {
            try{
                mfr.deleteById(feedId);
            } catch(EmptyResultDataAccessException e){
                throw new RemoveException("피드 삭제 중 오류가 발생했습니다");
            }
        } else {
            throw new FindException("해당 피드를 찾을 수 없습니다");
        }
    }

}
