package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOurplaceFeed is a Querydsl query type for OurplaceFeed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOurplaceFeed extends EntityPathBase<OurplaceFeed> {

    private static final long serialVersionUID = -621503836L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOurplaceFeed ourplaceFeed = new QOurplaceFeed("ourplaceFeed");

    public final com.kosa.mapbegood.domain.common.entity.QAuditEntity _super = new com.kosa.mapbegood.domain.common.entity.QAuditEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final QOurplaceFeedEmbedded id;

    public final com.kosa.mapbegood.domain.member.entity.QMember member;

    public final com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace ourplace;

    public QOurplaceFeed(String variable) {
        this(OurplaceFeed.class, forVariable(variable), INITS);
    }

    public QOurplaceFeed(Path<? extends OurplaceFeed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOurplaceFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOurplaceFeed(PathMetadata metadata, PathInits inits) {
        this(OurplaceFeed.class, metadata, inits);
    }

    public QOurplaceFeed(Class<? extends OurplaceFeed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QOurplaceFeedEmbedded(forProperty("id")) : null;
        this.member = inits.isInitialized("member") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("member")) : null;
        this.ourplace = inits.isInitialized("ourplace") ? new com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace(forProperty("ourplace"), inits.get("ourplace")) : null;
    }

}

