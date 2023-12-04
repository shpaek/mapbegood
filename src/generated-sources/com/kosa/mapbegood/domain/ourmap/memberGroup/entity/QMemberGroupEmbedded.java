package com.kosa.mapbegood.domain.ourmap.memberGroup.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberGroupEmbedded is a Querydsl query type for MemberGroupEmbedded
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberGroupEmbedded extends BeanPath<MemberGroupEmbedded> {

    private static final long serialVersionUID = 2078804898L;

    public static final QMemberGroupEmbedded memberGroupEmbedded = new QMemberGroupEmbedded("memberGroupEmbedded");

    public final StringPath email = createString("email");

    public final NumberPath<Long> groupsId = createNumber("groupsId", Long.class);

    public QMemberGroupEmbedded(String variable) {
        super(MemberGroupEmbedded.class, forVariable(variable));
    }

    public QMemberGroupEmbedded(Path<? extends MemberGroupEmbedded> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberGroupEmbedded(PathMetadata metadata) {
        super(MemberGroupEmbedded.class, metadata);
    }

}

