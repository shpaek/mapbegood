package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberInfoDTO;
import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;

import java.util.List;

public interface MemberCustomRepository {
    List<MemberInfoDTO> memberSearch(String nick);
}
