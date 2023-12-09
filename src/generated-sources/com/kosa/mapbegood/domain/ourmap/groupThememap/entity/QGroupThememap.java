package com.kosa.mapbegood.domain.ourmap.groupThememap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupThememap is a Querydsl query type for GroupThememap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupThememap extends EntityPathBase<GroupThememap> {

    private static final long serialVersionUID = -1531364072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupThememap groupThememap = new QGroupThememap("groupThememap");

    public final StringPath color = createString("color");

    public final com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups groupId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final ListPath<com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace> ourplaceList = this.<com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace>createList("ourplaceList", com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace.class, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace.class, PathInits.DIRECT2);

    public QGroupThememap(String variable) {
        this(GroupThememap.class, forVariable(variable), INITS);
    }

    public QGroupThememap(Path<? extends GroupThememap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupThememap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupThememap(PathMetadata metadata, PathInits inits) {
        this(GroupThememap.class, metadata, inits);
    }

    public QGroupThememap(Class<? extends GroupThememap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupId = inits.isInitialized("groupId") ? new com.kosa.mapbegood.domain.ourmap.groups.entity.QGroups(forProperty("groupId")) : null;
    }

}

