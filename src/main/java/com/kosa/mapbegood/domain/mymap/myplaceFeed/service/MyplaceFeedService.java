package com.kosa.mapbegood.domain.mymap.myplaceFeed.service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.entity.MyplaceFeed;
import com.kosa.mapbegood.domain.mymap.myplaceFeed.repository.MyplaceFeedRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyplaceFeedService {

    @Autowired
    private MyplaceFeedRepository mfr;

    /**
     * DTO 객체 타입의 피드 작성자 닉네임을 entity 타입으로 변환한다
     * @param feedDto 피드
     * @return 멤버 엔티티
     */
    public Member getMemberNickname(MyplaceFeedDTO feedDto){
        return Member
                .builder()
                .nickname(feedDto.getMemberNickname())
                .build();
    }

    /**
     * 내 피드를 조회할 수 있다
     * @param myplaceId 마이플레이스id
     * @return 피드
     * @throws FindException
     */
    public MyplaceFeedDTO findMyFeedById(Long myplaceId) throws FindException {
        Optional<MyplaceFeed> feed = mfr.findById(myplaceId);
        MyplaceFeedDTO feedDto = new MyplaceFeedDTO();
        feedDto.setMyplaceId(myplaceId);
        feedDto.setMemberNickname(String.valueOf(feed.get().getMemberNickname()));
        feedDto.setContent(feed.get().getContent());
        feedDto.setCreatedAt(feed.get().getCreatedAt());
        return feedDto;
    }

    /**
     * 내 피드를 생성할 수 있다
     * @param feedDto 피드
     * @throws AddException
     */
    public void createMyFeed(MyplaceFeedDTO feedDto) throws AddException {
        MyplaceFeed mf = MyplaceFeed
                .builder()
                .id(feedDto.getMyplaceId())
                .memberNickname(getMemberNickname(feedDto))
                .content(feedDto.getContent())
                .build();
        mfr.save(mf);

    }


    /**
     * 내 피드를 수정할 수 있다
     * @param feedDto 피드
     * @throws FindException
     * @throws AddException
     */
    public void updateMyFeed(MyplaceFeedDTO feedDto) throws FindException, ModifyException {
        MyplaceFeedDTO mfDto = findMyFeedById(feedDto.getMyplaceId());

        MyplaceFeed mf = MyplaceFeed
                .builder()
                .id(mfDto.getMyplaceId())
                .memberNickname(getMemberNickname(mfDto))
                .content(feedDto.getContent())
                .build();

        mfr.save(mf);
    }

    /**
     * 내 피드를 삭제할 수 있다
     * @param feedId 마이플레이스id
     * @throws RemoveException
     */
    public void deleteMyFeed(Long feedId) throws RemoveException {
        mfr.deleteById(feedId);
    }

}
