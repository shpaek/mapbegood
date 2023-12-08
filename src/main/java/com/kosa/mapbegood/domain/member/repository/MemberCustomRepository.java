package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;

import java.util.List;

public interface MemberCustomRepository {
    List<MemberSearchResponseDTO> memberSearch(String nick);
}
