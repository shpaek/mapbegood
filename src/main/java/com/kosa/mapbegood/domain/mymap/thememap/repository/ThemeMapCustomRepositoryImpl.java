package com.kosa.mapbegood.domain.mymap.thememap.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ThemeMapCustomRepositoryImpl implements ThemeMapCustomRepository {
    private final JPAQueryFactory queryFactory;


}
