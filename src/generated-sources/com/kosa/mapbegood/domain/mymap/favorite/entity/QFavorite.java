package com.kosa.mapbegood.domain.mymap.favorite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = -524369924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final com.kosa.mapbegood.domain.common.entity.QAuditEntity _super = new com.kosa.mapbegood.domain.common.entity.QAuditEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    public final QFavoriteEmbedded id;

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public final com.kosa.mapbegood.domain.mymap.thememap.entity.QThemeMap thememapId;

    public QFavorite(String variable) {
        this(Favorite.class, forVariable(variable), INITS);
    }

    public QFavorite(Path<? extends Favorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorite(PathMetadata metadata, PathInits inits) {
        this(Favorite.class, metadata, inits);
    }

    public QFavorite(Class<? extends Favorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QFavoriteEmbedded(forProperty("id")) : null;
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
        this.thememapId = inits.isInitialized("thememapId") ? new com.kosa.mapbegood.domain.mymap.thememap.entity.QThemeMap(forProperty("thememapId"), inits.get("thememapId")) : null;
    }

}

