
package com.kosa.mapbegood.domain.ourmap.groups.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.service.GroupsService;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;
import com.kosa.mapbegood.util.AuthenticationUtil;

@RestController
@RequestMapping("/group")
public class GroupsController {
	private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private GroupsService gs;
	@Autowired
	private AuthenticationUtil authenticationUtil;
	
	@GetMapping(value="", produces="application/json;charset=UTF-8")
	public List<GroupsDTO> findAllGroupsByMemberNickname(@RequestBody Map<String,String> map) throws FindException{
		//ㄴRequestBody로 보내야 응답결과를 json으로 반환이 가능함
		//ㄴㄴRequestBody -> "memberEmail":"사용자의 이메일값"이라는 객체가 받아지기 때문에 Map<>으로 받음
		//ㄴㄴㄴ따라서 메서드에 변수를 넣어줄 때 아래와 같이 넣어줘야함
		return gs.findAllGroupsByMemberEmail(map.get("memberEmail"));
	}
	
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> createGroup(Authentication authentication, 
			String name, @RequestPart MultipartFile image) throws AddException{ //그룹이미지도 받아야 해서 formdata로 받음
		//파일은 요청요청바디로만 보낼 수 있어서 GET방식을 못 쓰고 POST방식으로만 보낼 수 있다
		//프론트에서 파일테이터를 back으로 보내면 formdata형태로 보내게 된다
		//formdata를 받을때는 @RequestBody를 못쓰고(아니니까) 위의 메서드처럼 데이터를 하나씩 받아야 한다
		MemberGroupDTO memberGroupDto = new MemberGroupDTO();
		String email = authenticationUtil.getUserEmail(authentication);
		MemberDTO memberDto = new MemberDTO();
		memberDto.setEmail(email); //토큰에서 받은 이메일로 멤버설정
		memberGroupDto.setMember(memberDto); //그룹멤버로 멤버세팅
		GroupsDTO groupDto = new GroupsDTO();
		groupDto.setName(name); //그룹명을 그룹에 세팅
		memberGroupDto.setGroups(groupDto); //그룹멤버에 그룹세팅
		try{
			if(image==null&&image.getSize()<0) {				
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //올바르지 않은 이미지 첨부
			}else {
				gs.createGroup(memberGroupDto, image); //그룹생성, 그룹멤버의 멤버로 그룹장 자동 추가			
				return new ResponseEntity<>(HttpStatus.OK); //그룹생성성공			
			}
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //그룹생성실패
		}
	}
	
//	@PostMapping(value="", produces="application/json;charset=UTF-8")
//	public ResponseEntity<?> createGroup(@RequestBody MemberGroupDTO memberGroupDto) throws AddException{
//		try{
//			gs.createGroup(memberGroupDto);
//			return new ResponseEntity<>(HttpStatus.OK);
//		}catch(AddException e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
	@PutMapping(value="/{id}/group-image", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> updateGroupImage(@PathVariable Long id, @RequestPart MultipartFile image) throws Exception{
		try {
			if(image==null&&image.getSize()<0) {				
				throw new ModifyException("올바른 파일을 올려주세요");
			}else {
				gs.updateGroupImage(id, image);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value="/{id}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody GroupsDTO groupsDto) throws ModifyException{
		groupsDto.setId(id);
		try {
			gs.updateGroup(groupsDto);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(ModifyException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="/{id}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) throws RemoveException {
		try{
			gs.deleteGroup(id);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
