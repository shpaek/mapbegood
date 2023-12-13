package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberInfoDTO;
import com.kosa.mapbegood.domain.member.dto.QMemberInfoDTO;
import com.kosa.mapbegood.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberInfoDTO> memberSearch(String nick) {
        QMember qm = new QMember("qm");

        return queryFactory
                .select(new QMemberInfoDTO(qm.email, qm.nickname, qm.profileImage))
                .from(qm)
                .where(qm.nickname.contains(nick))
                .fetch();

//        QThemeMap qtm = new QThemeMap("qtm");
//
//        return queryFactory
//                .from(qm)
//                .leftJoin(qtm)
//                .on(qm.email.eq(qtm.memberEmail.email))
//                .where(qm.nickname.contains(nick))
//                .transform(
//                        groupBy(qm.email).list(
//                                new QMemberSearchResponseDTO(
//                                        qm.email,
//                                        qm.nickname,
//                                        qm.profileImage,
//                                        list(
//                                                new QThemeMapResponseDTO(
//                                                        qtm.id,
//                                                        qtm.name,
//                                                        qtm.color,
//                                                        qtm.memo
//                                                )
//                                        )
//                                )
//                        )
//                );
    }
}
