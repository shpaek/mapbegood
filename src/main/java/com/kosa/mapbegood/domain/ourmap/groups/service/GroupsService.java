
package com.kosa.mapbegood.domain.ourmap.groups.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kosa.mapbegood.domain.common.service.AwsS3Service;
import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.groups.dto.GroupsDTO;
import com.kosa.mapbegood.domain.ourmap.groups.entity.Groups;
import com.kosa.mapbegood.domain.ourmap.groups.repository.GroupsRepository;
import com.kosa.mapbegood.domain.ourmap.memberGroup.dto.MemberGroupDTO;
import com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup;
import com.kosa.mapbegood.domain.ourmap.memberGroup.repository.MemberGroupRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.exception.ModifyException;
import com.kosa.mapbegood.exception.RemoveException;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@Service
public class GroupsService {
	private final String groupImageUploadPath = "/group-image";
	@Autowired
	private AwsS3Service awsS3Service;
	@Autowired
	private GroupsRepository gr;
	@Autowired
	private MemberGroupRepository mgr;
	
	/**
	 * GroupsDTO를 Groups(엔터티)로 변환한다
	 * @param groupsDto
	 * @return Groups
	 */
	public Groups groupsDtoToEntity(GroupsDTO groupsDto) {
		Groups entity = new Groups();
		entity.setId(groupsDto.getId());
		entity.setName(groupsDto.getName());
//		entity.setLeaderEmail(groupsDto.getLeaderEmail());
		return entity;	
	}
	
	//vo->dto 내 그룹 전체검색, 그룹하나만 검색은 GroupService
	//		  그룹에 해당하는 사용자들 검색은 MemberGroupService
	//그룹 전체검색
	/**
	 * List<GroupsDTO>를 반환한다
	 * @param memberEmail
	 * @return 이메일 사용자가 속한 모든 그룹의 그룹명과 그룹장의 닉네임
	 * @throws FindException
	 */
	public List<GroupsDTO> findAllGroupsByMemberEmail(String memberEmail) throws FindException{
		Member member = new Member();		
		member.setEmail(memberEmail);
		try {
			List<MemberGroup> memberGroups = mgr.findByMemberEmail(member);//이메일 사용자가 속해있는 멤버그룹들
			if(memberGroups==null) {
				throw new FindException("소속된 그룹이 없습니다");
			}
			List<GroupsDTO> resultGroupDTO = new ArrayList<>();//이메일 사용자가 속한 그룹들
			for(int i=0; i<memberGroups.size(); i++) {
				//그룹정보 셋팅
				MemberGroup membergroup = memberGroups.get(i); //이메일 사용자가 속해있는 그룹1개
				Groups group = membergroup.getGroupId();
				GroupsDTO groupDTO = new GroupsDTO();
				groupDTO.setId(group.getId());
				groupDTO.setName(group.getName());
//				groupDTO.setGroupImage(group.getGroupImage()); //그룹이미지
				
				List<MemberGroup>members = group.getMemberGroupList(); //각 그룹의 멤버들
				for(MemberGroup mg: members) {
					if(mg.getLeader() == 1) { //리더인 경우
						//String leaderNickName = members.get(0).getMemberEmail().getNickname();
						MemberDTO leaderMemberDTO = new MemberDTO();
						leaderMemberDTO.setNickname( members.get(0).getMemberEmail().getNickname());
		
						MemberGroupDTO memberGroupDTO = new MemberGroupDTO();
						memberGroupDTO.setMember(leaderMemberDTO);
						memberGroupDTO.setLeader(mg.getLeader());
						
						List<MemberGroupDTO> listGroupMemberDTO = new ArrayList<>();
						listGroupMemberDTO.add(memberGroupDTO);
						groupDTO.setMemberGroupList(listGroupMemberDTO);
					}			
				}
				
				log.error("groupDTO.getId={}, groupDTO.getName={},leaderNickname={} ", groupDTO.getId(), groupDTO.getName(), groupDTO.getMemberGroupList().get(0).getMember().getNickname());
				resultGroupDTO.add(groupDTO);
			}
			return resultGroupDTO;
		}catch(Exception e) {
			new FindException(e.getMessage());
			return null;
		}
	}
	
	
	
	/**
	 * 사용자가 그룹을 생성한다
	 * @param groupsDto
	 * @throws AddException
	 */
	public void createGroup(MemberGroupDTO memberGroupDto, MultipartFile groupImage) throws AddException{
		//-------그룹 생성 START-------
		GroupsDTO groupDto = memberGroupDto.getGroups();
		Groups entity = groupsDtoToEntity(groupDto);
//		log.error("memberGroupDto.getGroups().getName()={}", memberGroupDto.getGroups().getName() );
		gr.save(entity); //사용자가 그룹을 생성하면 그룹을 생성한 사용자가 자동으로 그룹멤버의 그룹장으로 추가되어야함
		//-------그룹 생성 END-------
		//-------그룹장 추가 START-------
		MemberGroup mgEntity = new MemberGroup();
		mgEntity.setGroupId(entity);
		Member mEntity = new Member();
		mEntity.setEmail(memberGroupDto.getMember().getEmail());
		mgEntity.setMemberEmail(mEntity);
		mgEntity.setLeader(1);
		mgr.save(mgEntity);
		//-------그룹장 추가 END-------
		//-------그룹이미지 추가 START-------
		Long groupId = entity.getId(); // 새로 생성된 그룹의 ID
		try {
			updateGroupImage(groupId, groupImage);
		} catch (Exception e) {
			throw new AddException("그룹생성에 실패했습니다: "+e.getMessage());
		}
		//-------그룹이미지 추가 END-------
	}
	
	
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 amazonS3;
	/**
	 * 그룹장이 그룹이미지를 수정한다
	 * @param groupId
	 * @param groupImage
	 * @throws Exception
	 */
	public void updateGroupImage(Long groupId, MultipartFile groupImage) throws Exception{
		GroupsDTO groupDto = new GroupsDTO();
		groupDto.setId(groupId);
		Optional<Groups> optEntity = gr.findById(groupId);
		optEntity.orElseThrow(()->
			new ModifyException("그룹이 없어 수정이 불가능합니다")
		);
		Groups entity = optEntity.get();
		try {
//			File targetFile = new File("D:\\KOSA202307\\attaches", groupId+groupImage.getOriginalFilename());
//			FileCopyUtils.copy(groupImage.getBytes(), targetFile);
			//--------그룹이미지 파일 만들기 START---------
			String imageUrl = awsS3Service.uploadImage(groupImage, groupImageUploadPath);
			groupImage.getContentType();
			String extName = groupImage.getOriginalFilename().substring(groupImage.getOriginalFilename().lastIndexOf("."));
			log.error(imageUrl);
			//--------그룹이미지 파일 만들기 END---------
			//--------그룹섬네일 파일 만들기 START---------
			int width=150;
			int height=150;
			String thumbFileName = groupId+"_groupImage.jpg"; //+  groupImage.getOriginalFilename() + extName; //그룹이미지 파일명
//			png파일을 업로드 해도 jpg로 저장이 됨!
			File thumbFile = new File("C:\\Users\\COM\\Downloads", thumbFileName);  //실제: groupImageUploadPath, thumbFileName
			FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);//출력스트림
			URL url = new URL(imageUrl);
			InputStream thumbnailIS =  url.openStream();//imageUrl);
			Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height); //섬네일파일생성
			//--------그룹섬네일 파일 만들기 END---------
			//--------그룹섬네일 파일 올리기 START---------
			FileInputStream inputStream   = new FileInputStream(thumbFile);
			ObjectMetadata objectMetadata = new ObjectMetadata();
		    objectMetadata.setContentLength(thumbFile.length());
		    objectMetadata.setContentType(Files.probeContentType(thumbFile.toPath()));
		    PutObjectRequest putObjectRequest =
	                new PutObjectRequest(bucket.concat(groupImageUploadPath), thumbFileName, inputStream, objectMetadata)
	                        .withCannedAcl(CannedAccessControlList.PublicRead);
		    amazonS3.putObject(putObjectRequest);
	        
	        log.error("bucket.concat(groupImageUploadPath)={}, thumbFileName={}, objectMetadata.setContentLength={}, objectMetadata.setContentType()={}",
	        		bucket.concat(groupImageUploadPath), thumbFileName, objectMetadata.getContentLength(), objectMetadata.getContentType());
	        
	        thumbnailOS.close();
	        thumbnailIS.close();
	      //--------그룹섬네일 파일 올리기 START---------
		}catch(Exception e){
			throw new ModifyException("그룹이미지 수정이 불가능합니다");
		}
	}
	
	
	/**
	 * 그룹장이 그룹명을 수정한다
	 * @param groupsDto
	 * @throws ModifyException
	 */
	public void updateGroup(GroupsDTO groupsDto) throws ModifyException{
		//find로 그룹이 있는지 찾고 수정!
		Optional<Groups> optEntity = gr.findById(groupsDto.getId()); 
		optEntity.orElseThrow(()->
			new ModifyException("그룹이 없어 수정이 불가능합니다")
		);
		Groups entity = optEntity.get();
		entity.modifyGroupName(groupsDto.getName());
		gr.save(entity);
	}
	
	public void uploadGroupImage(MultipartFile groupImage) throws Exception{
		
	}

	
	/**
	 * 그룹이름이 같은 게 존재하지 않으면 FindException발생
	 * @param groupName 
	 */
	public void dupchkGroupName(String groupName) throws FindException {
		Optional<Groups> optEntity = gr.findByName(groupName);
		optEntity.orElseThrow(()->
			new FindException("이미 존재하는 그룹명입니다")
		);
	}
	
	
	/**
	 * 그룹장이 그룹을 삭제한다
	 * @param groupId
	 * @throws RemoveException
	 */
	public void deleteGroup(Long id) throws RemoveException{
		//find로 그룹이 있는지 찾고 삭제!
		Optional<Groups> optEntity = gr.findById(id);
		if(optEntity.isPresent()) {
			try {
				gr.deleteById(id);							
			}catch(Exception e) {
				throw new RemoveException(e.getMessage());
			}
		}else {
			throw new RemoveException("그룹이 없어 삭제가 불가능합니다");
		}
	}

}
