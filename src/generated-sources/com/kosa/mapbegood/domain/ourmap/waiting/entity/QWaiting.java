package com.kosa.mapbegood.domain.ourmap.waiting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaiting is a Querydsl query type for Waiting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaiting extends EntityPathBase<Waiting> {

    private static final long serialVersionUID = -1639829512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaiting waiting = new QWaiting("waiting");

    public final com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups groupId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberEmail = createString("memberEmail");

    public QWaiting(String variable) {
        this(Waiting.class, forVariable(variable), INITS);
    }

    public QWaiting(Path<? extends Waiting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaiting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaiting(PathMetadata metadata, PathInits inits) {
        this(Waiting.class, metadata, inits);
    }

    public QWaiting(Class<? extends Waiting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupId = inits.isInitialized("groupId") ? new com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups(forProperty("groupId")) : null;
    }

}

