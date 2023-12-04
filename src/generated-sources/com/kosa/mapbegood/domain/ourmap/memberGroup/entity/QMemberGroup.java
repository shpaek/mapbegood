package com.kosa.mapbegood.domain.ourmap.memberGroup.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberGroup is a Querydsl query type for MemberGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberGroup extends EntityPathBase<MemberGroup> {

    private static final long serialVersionUID = 875496696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberGroup memberGroup = new QMemberGroup("memberGroup");

    public final com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups groupId;

    public final QMemberGroupEmbedded id;

    public final NumberPath<Integer> leader = createNumber("leader", Integer.class);

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public QMemberGroup(String variable) {
        this(MemberGroup.class, forVariable(variable), INITS);
    }

    public QMemberGroup(Path<? extends MemberGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberGroup(PathMetadata metadata, PathInits inits) {
        this(MemberGroup.class, metadata, inits);
    }

    public QMemberGroup(Class<? extends MemberGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupId = inits.isInitialized("groupId") ? new com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups(forProperty("groupId")) : null;
        this.id = inits.isInitialized("id") ? new QMemberGroupEmbedded(forProperty("id")) : null;
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
    }

}

