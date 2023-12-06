package com.kosa.mapbegood.domain.member.repository;

import org.springframework.stereotype.Repository;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public void test() {
        Member member = new Member();

        QMember qm = new QMember("test");

        Member member1 = queryFactory.selectFrom(qm).fetchFirst();
    }
}
