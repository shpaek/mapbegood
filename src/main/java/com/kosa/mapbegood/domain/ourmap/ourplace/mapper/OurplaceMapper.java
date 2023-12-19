package com.kosa.mapbegood.domain.ourmap.ourplace.mapper;

import com.kosa.mapbegood.domain.member.dto.MemberDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.ourmap.ourplace.dto.OurplaceDTO;
import com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace;
import com.kosa.mapbegood.domain.place.dto.PlaceDTO;
import com.kosa.mapbegood.domain.place.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.sql.Date;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OurplaceMapper {
    default Ourplace dtoToEntity(OurplaceDTO ourplaceDto){
        if ( ourplaceDto == null ) {
            return null;
        }

        Ourplace ourplace = new Ourplace();

        ourplace.setGroupThememapId( ourplaceDto.getGroupThememapId() );
        ourplace.setId( ourplaceDto.getId() );
        ourplace.setMemberEmail( memberDTOToMember( ourplaceDto.getMemberEmail() ) );
        ourplace.setPlaceId( placeDTOToPlace( ourplaceDto.getPlaceId() ) );
        if ( ourplaceDto.getVisitedAt() != null ) {
            ourplace.setVisitedAt( new Date( ourplaceDto.getVisitedAt().getTime() ) );
        }

        return ourplace;
    };

    default OurplaceDTO entityToDto(Ourplace ourplace){
        if ( ourplace == null ) {
            return null;
        }

        OurplaceDTO.OurplaceDTOBuilder ourplaceDTO = OurplaceDTO.builder();

        ourplaceDTO.groupThememapId( ourplace.getGroupThememapId() );
        ourplaceDTO.id( ourplace.getId() );
        ourplaceDTO.memberEmail( memberToMemberDTO( ourplace.getMemberEmail() ) );
        ourplaceDTO.placeId( placeToPlaceDTO( ourplace.getPlaceId() ) );
        ourplaceDTO.visitedAt( ourplace.getVisitedAt() );

        return ourplaceDTO.build();
    };

    private Member memberDTOToMember(MemberDTO memberDTO) {
        if ( memberDTO == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.email( memberDTO.getEmail() );
        member.nickname( memberDTO.getNickname() );
        member.password( memberDTO.getPassword() );
        member.profileImage( memberDTO.getProfileImage() );

        return member.build();
    }

    private Place placeDTOToPlace(PlaceDTO placeDTO) {
        if ( placeDTO == null ) {
            return null;
        }

        Place place = new Place();

        place.setAddress( placeDTO.getAddress() );
        place.setCategory( placeDTO.getCategory() );
        place.setId( placeDTO.getId() );
        place.setPlaceName( placeDTO.getPlaceName() );
        place.setX( placeDTO.getX() );
        place.setY( placeDTO.getY() );

        return place;
    }

    private MemberDTO memberToMemberDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO.MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.email( member.getEmail() );
        memberDTO.nickname( member.getNickname() );
        memberDTO.password( member.getPassword() );
        memberDTO.profileImage( member.getProfileImage() );

        return memberDTO.build();
    }

    private PlaceDTO placeToPlaceDTO(Place place) {
        if ( place == null ) {
            return null;
        }

        PlaceDTO placeDTO = new PlaceDTO();

        placeDTO.setAddress( place.getAddress() );
        placeDTO.setCategory( place.getCategory() );
        placeDTO.setId( place.getId() );
        placeDTO.setPlaceName( place.getPlaceName() );
        placeDTO.setX( place.getX() );
        placeDTO.setY( place.getY() );

        return placeDTO;
    }
}
