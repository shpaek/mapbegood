package com.kosa.mapbegood.domain.mymap.myplaceFeed.service;

import com.kosa.mapbegood.domain.mymap.myplaceFeed.dto.MyplaceFeedDTO;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

public interface MyplaceFeedServiceInterface {

    /**
     * 내 피드를 조회할 수 있다
     * @param myplaceId 마이플레이스id
     * @return 피드
     * @throws FindException
     */
    public MyplaceFeedDTO findMyFeedById(Long myplaceId) throws FindException;

    /**
     * 내 피드를 생성할 수 있다
     * @param feedDto 피드
     * @throws AddException
     */
    public void createMyFeed(MyplaceFeedDTO feedDto) throws AddException;


    /**
     * 내 피드를 수정할 수 있다
     * @param feedDto 피드
     * @throws FindException
     * @throws AddException
     */
    public void updateMyFeed(MyplaceFeedDTO feedDto) throws FindException, ModifyException;

    /**
     * 내 피드를 삭제할 수 있다
     * @param feedId 마이플레이스id
     * @throws RemoveException
     */
    public void deleteMyFeed(Long feedId) throws RemoveException, FindException;

}
