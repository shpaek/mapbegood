package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.dto.QMemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    public void test() {
        QMember qm = new QMember("test");
        Member member1 = queryFactory.selectFrom(qm).fetchFirst();

//        List<MemberSearchResponseDTO> memberResponseDTOList = queryFactory
//                .select(new QMemberSearchResponseDTO(qm.nickname, qm.profileImage))
//                .from(qm)
//                .fetch();
    }
}
