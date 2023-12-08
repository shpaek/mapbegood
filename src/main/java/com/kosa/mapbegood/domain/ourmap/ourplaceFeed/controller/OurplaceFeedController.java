package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.controller;

import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.service.MemberGroupService;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service.OurplaceFeedService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ourfeed")
public class OurplaceFeedController {

    @Autowired
    private OurplaceFeedService service;

    @Autowired
    private MemberGroupService mgService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    public OurplaceFeedDTO setOurfeedEmId(Long ourplaceId, String memberEmail){
        OurplaceFeedDTO feedDto = new OurplaceFeedDTO();
        feedDto.setOurplaceId(ourplaceId);
        feedDto.setMemberEmail(memberEmail);
        return feedDto;
    }

    //GET	/ourfeed/{ourplaceId}
    @GetMapping("/{groupId}/{ourplaceId}")
    public ResponseEntity<?> findAll(@PathVariable Long groupId, @PathVariable Long ourplaceId) {
        try {
            List<MemberGroupDTO> members = mgService.findAllGroupMembersByGroupId(groupId);
            List<Object> ourFeedList = new ArrayList<>();

            for (MemberGroupDTO member : members) {
                OurplaceFeedEmbedded ourfeedId = new OurplaceFeedEmbedded();
                ourfeedId.setOurplaceId(ourplaceId);
                ourfeedId.setEmail(member.getMember().getEmail());

                OurplaceFeedDTO ourfeedDto = service.findOurFeedById(ourfeedId);

                List<Object> ourFeed = List.of(ourfeedDto);
                ourFeedList.add(ourFeed);
            }

            return ResponseEntity.ok(ourFeedList);
        } catch (FindException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾지 못했습니다");
        }
    }


    //GET	/ourfeed/{ourplaceId}/{memberNickname}
    @GetMapping("/{ourplaceId}/{memberNickname}")
    public ResponseEntity<?> find(Authentication authentication, @PathVariable Long ourplaceId) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, email);
            OurplaceFeedDTO feed = service.findOurFeedById(service.getOurFeedEmId(feedDto));

            String imgId = ourplaceId.toString() + email;

            return ResponseEntity.ok(List.of(feed));
        } catch (FindException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾지 못했습니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
        }
    }


    //POST	/ourfeed/{ourplaceId}/{memberNickname}
    @PostMapping(value = "/{ourplaceId}/{memberNickname}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> create(Authentication authentication, @RequestBody OurplaceFeedDTO feedDto,
                                    @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            feedDto.setMemberEmail(email);
            String imgId = feedDto.getOurplaceId().toString() + email;

            // OurplaceFeed 등록
            service.createOurFeed(feedDto);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/html;charset=UTF-8");
            return new ResponseEntity<>("작성완료",
                    headers,
                    HttpStatus.OK);
        } catch (AddException e) {
            return new ResponseEntity<>("작성 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //PUT	/ourfeed/{ourplaceId}/{memberNickname}
    @PutMapping(value = "/{ourplaceId}/{memberNickname}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> update(Authentication authentication, @RequestBody OurplaceFeedDTO feedDto) throws ModifyException, FindException {
        String email = authenticationUtil.getUserEmail(authentication);
        feedDto.setMemberEmail(email);
        service.updateOurFeed(feedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE	/ourfeed/{ourplaceId}/{memberNickname}
    @DeleteMapping("/{ourplaceId}/{memberNickname}")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable Long ourplaceId) throws RemoveException, FindException {
        String email = authenticationUtil.getUserEmail(authentication);
        OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, email);
        service.deleteOurFeed(service.getOurFeedEmId(feedDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
