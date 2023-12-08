package com.kosa.mapbegood.domain.ourmap.waiting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWaiting is a Querydsl query type for Waiting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaiting extends EntityPathBase<Waiting> {

    private static final long serialVersionUID = -1639829512L;

    public static final QWaiting waiting = new QWaiting("waiting");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberEmail = createString("memberEmail");

    public QWaiting(String variable) {
        super(Waiting.class, forVariable(variable));
    }

    public QWaiting(Path<? extends Waiting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWaiting(PathMetadata metadata) {
        super(Waiting.class, metadata);
    }

}

