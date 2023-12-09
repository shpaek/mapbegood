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

    public final com.kosa.mapbegood.domain.common.entity.QAuditEntity _super = new com.kosa.mapbegood.domain.common.entity.QAuditEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    public final com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap groupThememapId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public final com.kosa.mapbegood.domain.place.entity.QPlace placeId;

    public final DatePath<java.sql.Date> visitedAt = createDate("visitedAt", java.sql.Date.class);

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
        this.groupThememapId = inits.isInitialized("groupThememapId") ? new com.kosa.mapbegood.domain.ourmap.groupThememap.entity.QGroupThememap(forProperty("groupThememapId"), inits.get("groupThememapId")) : null;
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
        this.placeId = inits.isInitialized("placeId") ? new com.kosa.mapbegood.domain.place.entity.QPlace(forProperty("placeId")) : null;
    }

}

