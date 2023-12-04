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

    public static final QGroupThememap groupThememap = new QGroupThememap("groupThememap");

    public final StringPath color = createString("color");

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final ListPath<com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace> ourplaceList = this.<com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace>createList("ourplaceList", com.kosa.mapbegood.domain.ourmap.ourplace.entity.Ourplace.class, com.kosa.mapbegood.domain.ourmap.ourplace.entity.QOurplace.class, PathInits.DIRECT2);

    public QGroupThememap(String variable) {
        super(GroupThememap.class, forVariable(variable));
    }

    public QGroupThememap(Path<? extends GroupThememap> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupThememap(PathMetadata metadata) {
        super(GroupThememap.class, metadata);
    }

}

