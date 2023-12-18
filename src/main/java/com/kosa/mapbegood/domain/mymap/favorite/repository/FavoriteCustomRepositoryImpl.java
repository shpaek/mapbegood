package com.kosa.mapbegood.domain.mymap.favorite.repository;

import com.kosa.mapbegood.domain.member.entity.QMember;
import com.kosa.mapbegood.domain.mymap.favorite.dto.QRecommendThemeMapDto;
import com.kosa.mapbegood.domain.mymap.favorite.dto.RecommendThemeMapDto;
import com.kosa.mapbegood.domain.mymap.favorite.entity.QFavorite;
import com.kosa.mapbegood.domain.mymap.thememap.entity.QThemeMap;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FavoriteCustomRepositoryImpl implements FavoriteCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecommendThemeMapDto> findReCommendThemeMap(int pageNum) {
        QThemeMap qtm = new QThemeMap("qtm");
        QFavorite qf = new QFavorite("qf");
        QMember qm = new QMember("qm");

        return queryFactory
                .select(new QRecommendThemeMapDto(qf.count(), qtm.id, qtm.name, qtm.memo, qm.nickname))
                .from(qf)
                .join(qtm).on(qf.thememapId.eq(qtm))
                .join(qm).on(qtm.memberEmail.eq(qm))
                .groupBy(qtm.id, qtm.name, qtm.memo, qm.nickname)
                .orderBy(qf.count().desc())
                .offset((pageNum * 10) - 10)
                .limit(10)
                .fetch();
    }
}
