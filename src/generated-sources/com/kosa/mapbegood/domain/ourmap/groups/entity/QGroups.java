package com.kosa.mapbegood.domain.ourmap.groups.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroups is a Querydsl query type for Groups
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroups extends EntityPathBase<Groups> {

    private static final long serialVersionUID = -982964198L;

    public static final QGroups groups = new QGroups("groups");

    public final StringPath groupImage = createString("groupImage");

    public final ListPath<com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap, com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap> groupThememapList = this.<com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap, com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap>createList("groupThememapList", com.kosa.mapbegood.domain.ourmap.groupThememap.entity.GroupThememap.class, com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup, com.kosa.mapbegood.domain.ourmap.memberGroup.entity.QMemberGroup> memberGroupList = this.<com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup, com.kosa.mapbegood.domain.ourmap.memberGroup.entity.QMemberGroup>createList("memberGroupList", com.kosa.mapbegood.domain.ourmap.memberGroup.entity.MemberGroup.class, com.kosa.mapbegood.domain.ourmap.memberGroup.entity.QMemberGroup.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting, com.kosa.mapbegood.domain.ourmap.waiting.entity.QWaiting> waitingList = this.<com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting, com.kosa.mapbegood.domain.ourmap.waiting.entity.QWaiting>createList("waitingList", com.kosa.mapbegood.domain.ourmap.waiting.entity.Waiting.class, com.kosa.mapbegood.domain.ourmap.waiting.entity.QWaiting.class, PathInits.DIRECT2);

    public QGroups(String variable) {
        super(Groups.class, forVariable(variable));
    }

    public QGroups(Path<? extends Groups> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroups(PathMetadata metadata) {
        super(Groups.class, metadata);
    }

}

