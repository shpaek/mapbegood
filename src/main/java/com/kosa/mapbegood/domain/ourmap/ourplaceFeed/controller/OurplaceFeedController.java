package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.controller;

import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service.OurplaceFeedService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ourfeed")
public class OurplaceFeedController {

    @Autowired
    private OurplaceFeedService service;

    public OurplaceFeedDTO setOurfeedEmId(Long ourplaceId, String memberNickname){
        OurplaceFeedDTO feedDto = new OurplaceFeedDTO();
        feedDto.setOurplaceId(ourplaceId);
        feedDto.setMemberNickname(memberNickname);
        return feedDto;
    }

    //GET	/ourfeed/{ourplaceId}
    @GetMapping("/{ourplaceId}")
    public List<OurplaceFeedDTO> findAll(@PathVariable Long ourplaceId) throws FindException {
        OurplaceFeedDTO feedDto = new OurplaceFeedDTO();
        feedDto.setOurplaceId(ourplaceId);
        OurplaceFeedEmbedded ofid = service.getOurFeedEmId(feedDto);
        return service.findAllOurFeed(List.of(ofid));
    }

    //GET	/ourfeed/{ourplaceId}/{memberNickname}
    @GetMapping("/{ourplaceId}/{memberNickname}")
    public OurplaceFeedDTO find(@PathVariable Long ourplaceId, @PathVariable String memberNickname) throws FindException{
        OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, memberNickname);
        return service.findOurFeedById(service.getOurFeedEmId(feedDto));
    }

    //POST	/ourfeed/{ourplaceId}/{memberNickname}
    @PostMapping(value = "/{ourplaceId}/{memberNickname}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> create(@PathVariable Long ourplaceId, @PathVariable String memberNickname, @RequestBody OurplaceFeedDTO feedDto) throws AddException {
        try {
            service.createOurFeed(feedDto);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/html;charset=UTF-8");
            return new ResponseEntity<>("작성완료",
                    headers,
                    HttpStatus.OK);
        } catch (AddException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //PUT	/ourfeed/{ourplaceId}/{memberNickname}
    @PutMapping(value = "/{ourplaceId}/{memberNickname}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> update(@PathVariable Long ourplaceId, @PathVariable String memberNickname, @RequestBody OurplaceFeedDTO feedDto) throws ModifyException, FindException {
        feedDto.setOurplaceId(ourplaceId);
        feedDto.setMemberNickname(memberNickname);
        service.updateOurFeed(feedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE	/ourfeed/{ourplaceId}/{memberNickname}
    @DeleteMapping("/{ourplaceId}/{memberNickname}")
    public ResponseEntity<?> delete(@PathVariable Long ourplaceId, @PathVariable String memberNickname) throws RemoveException {
        OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, memberNickname);
        service.deleteOurFeed(service.getOurFeedEmId(feedDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
