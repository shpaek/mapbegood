package com.kosa.mapbegood.domain.ourmap.ourplace.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOurplace is a Querydsl query type for Ourplace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOurplace extends EntityPathBase<Ourplace> {

    private static final long serialVersionUID = 651333160L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOurplace ourplace = new QOurplace("ourplace");

    public final StringPath category = createString("category");

    public final com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap groupThememapId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public final NumberPath<Long> placeId = createNumber("placeId", Long.class);

    public final StringPath placeName = createString("placeName");

    public final DatePath<java.sql.Date> visitedAt = createDate("visitedAt", java.sql.Date.class);

    public final NumberPath<Double> x = createNumber("x", Double.class);

    public final NumberPath<Double> y = createNumber("y", Double.class);

    public QOurplace(String variable) {
        this(Ourplace.class, forVariable(variable), INITS);
    }

    public QOurplace(Path<? extends Ourplace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOurplace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOurplace(PathMetadata metadata, PathInits inits) {
        this(Ourplace.class, metadata, inits);
    }

    public QOurplace(Class<? extends Ourplace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupThememapId = inits.isInitialized("groupThememapId") ? new com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap(forProperty("groupThememapId")) : null;
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
    }

}

