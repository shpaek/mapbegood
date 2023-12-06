package com.kosa.mapbegood.domain.mymap.myplaceFeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyplaceFeed is a Querydsl query type for MyplaceFeed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyplaceFeed extends EntityPathBase<MyplaceFeed> {

    private static final long serialVersionUID = -1665367542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyplaceFeed myplaceFeed = new QMyplaceFeed("myplaceFeed");

    public final com.kosa.mapbegood.domain.common.entity.QAuditEntity _super = new com.kosa.mapbegood.domain.common.entity.QAuditEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public final com.kosa.mapbegood.domain.mymap.myplace.entity.QMyplace myplaceId;

    public QMyplaceFeed(String variable) {
        this(MyplaceFeed.class, forVariable(variable), INITS);
    }

    public QMyplaceFeed(Path<? extends MyplaceFeed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyplaceFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyplaceFeed(PathMetadata metadata, PathInits inits) {
        this(MyplaceFeed.class, metadata, inits);
    }

    public QMyplaceFeed(Class<? extends MyplaceFeed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
        this.myplaceId = inits.isInitialized("myplaceId") ? new com.kosa.mapbegood.domain.mymap.myplace.entity.QMyplace(forProperty("myplaceId"), inits.get("myplaceId")) : null;
    }

}

