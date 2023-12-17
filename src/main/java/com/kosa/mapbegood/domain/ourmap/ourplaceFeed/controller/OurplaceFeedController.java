package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.service.MemberGroupService;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.dto.OurplaceFeedDTO;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity.OurplaceFeedEmbedded;
import com.kosa.mapbegood.domain.ourmap.ourplaceFeed.service.OurplaceFeedService;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;

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
        OurplaceDTO opDto = new OurplaceDTO();
        opDto.setId(ourplaceId);
        MemberDTO member = new MemberDTO();
        member.setEmail(memberEmail);
        feedDto.setOurplaceId(opDto);
        feedDto.setMemberEmail(member);
        return feedDto;
    }

    //GET	/ourfeed/{groupId}/{ourplaceId}
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
                if(ourfeedDto!=null) {
                List<Object> ourFeed = List.of(ourfeedDto);
                ourFeedList.add(ourFeed);
                }
            }

            return ResponseEntity.ok(ourFeedList);
        } catch (FindException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾지 못했습니다");
        }
    }


    //GET	/ourfeed/{groupId}/{ourplaceId}/{memberEmail}
    @GetMapping("/{groupId}/{ourplaceId}/{memberEmail}")
    public ResponseEntity<?> find(Authentication authentication, @PathVariable Long ourplaceId) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, email);
            OurplaceFeedDTO feed = service.findOurFeedById(service.getOurFeedEmId(feedDto));
            if(feed!=null) {
            return ResponseEntity.ok(feed);
            }else {
				return ResponseEntity.ok(new FindException("피드를 찾을 수 없습니다"));
			}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("피드를 찾지 못했습니다: " + e.getMessage());
        }
    }


    //POST	/ourfeed/{groupId}/{ourplaceId}/{memberEmail}
    @PostMapping(value = "/{groupId}/{ourplaceId}/{memberEmail}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> create(Authentication authentication, @RequestBody OurplaceFeedDTO feedDto) {
        try {
            String email = authenticationUtil.getUserEmail(authentication);
            MemberDTO member = new MemberDTO();
            member.setEmail(email);
            feedDto.setMemberEmail(member);
            System.out.println(email); 
            service.createOurFeed(feedDto);

            return ResponseEntity.ok("작성완료");
        } catch (AddException e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성 실패: " + e.getMessage());
        }
    }


    //PUT	/ourfeed/{groupId}/{ourplaceId}/{memberEmail}
    @PutMapping(value = "/{groupId}/{ourplaceId}/{memberEmail}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> update(Authentication authentication, @RequestBody OurplaceFeedDTO feedDto) throws ModifyException, FindException {
        String email = authenticationUtil.getUserEmail(authentication);
        MemberDTO member = new MemberDTO();
        member.setEmail(email);
        feedDto.setMemberEmail(member);

		try {
	        service.updateOurFeed(feedDto);
			return ResponseEntity.ok("수정 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패: " + e.getMessage());
		}
    }

    //DELETE	/ourfeed/{groupId}/{ourplaceId}/{memberEmail}
    @DeleteMapping("/{groupId}/{ourplaceId}/{memberEmail}")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable Long ourplaceId) throws RemoveException, FindException {
        String email = authenticationUtil.getUserEmail(authentication);
        OurplaceFeedDTO feedDto = setOurfeedEmId(ourplaceId, email);

		try {
	        service.deleteOurFeed(service.getOurFeedEmId(feedDto));
			return ResponseEntity.ok("삭제 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패: " + e.getMessage());
		}
    }
}
