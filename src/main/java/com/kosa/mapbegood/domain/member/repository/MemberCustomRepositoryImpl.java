package com.kosa.mapbegood.domain.member.repository;

import com.kosa.mapbegood.domain.member.dto.MemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.dto.QMemberSearchResponseDTO;
import com.kosa.mapbegood.domain.member.entity.QMember;
import com.kosa.mapbegood.domain.mymap.thememap.dto.QThemeMapResponseDTO;
import com.kosa.mapbegood.domain.mymap.thememap.entity.QThemeMap;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberSearchResponseDTO> memberSearch(String nick) {
        QMember qm = new QMember("qm");
        QThemeMap qtm = new QThemeMap("qtm");

        return queryFactory
                .from(qm)
                .leftJoin(qtm)
                .on(qm.email.eq(qtm.memberEmail.email))
                .where(qm.nickname.eq(nick))
                .transform(
                        groupBy(qm.email).list(
                                new QMemberSearchResponseDTO(
                                        qm.nickname,
                                        qm.profileImage,
                                        list(
                                                new QThemeMapResponseDTO(
                                                        qtm.id,
                                                        qtm.name,
                                                        qtm.color,
                                                        qtm.memo
                                                )
                                        )
                                )
                        )
                );
    }
}
