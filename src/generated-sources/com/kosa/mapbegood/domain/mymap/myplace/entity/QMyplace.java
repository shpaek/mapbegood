package com.kosa.mapbegood.domain.mymap.myplace.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyplace is a Querydsl query type for Myplace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyplace extends EntityPathBase<Myplace> {

    private static final long serialVersionUID = -1745743414L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyplace myplace = new QMyplace("myplace");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.mapbegood.domain.place.entity.QPlace placeId;

    public final NumberPath<Long> thememapId = createNumber("thememapId", Long.class);

    public final DatePath<java.sql.Date> visitedAt = createDate("visitedAt", java.sql.Date.class);

    public QMyplace(String variable) {
        this(Myplace.class, forVariable(variable), INITS);
    }

    public QMyplace(Path<? extends Myplace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyplace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyplace(PathMetadata metadata, PathInits inits) {
        this(Myplace.class, metadata, inits);
    }

    public QMyplace(Class<? extends Myplace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.placeId = inits.isInitialized("placeId") ? new com.kosa.mapbegood.domain.place.entity.QPlace(forProperty("placeId")) : null;
    }

}

