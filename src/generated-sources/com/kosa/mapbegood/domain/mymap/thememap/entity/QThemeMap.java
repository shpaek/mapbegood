package com.kosa.mapbegood.domain.mymap.thememap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThemeMap is a Querydsl query type for ThemeMap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThemeMap extends EntityPathBase<ThemeMap> {

    private static final long serialVersionUID = -8518518L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThemeMap themeMap = new QThemeMap("themeMap");

    public final StringPath color = createString("color");

    public final ListPath<com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite, com.kosa.mapbegood.domain.mymap.favorite.entity.QFavorite> favoriteList = this.<com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite, com.kosa.mapbegood.domain.mymap.favorite.entity.QFavorite>createList("favoriteList", com.kosa.mapbegood.domain.mymap.favorite.entity.Favorite.class, com.kosa.mapbegood.domain.mymap.favorite.entity.QFavorite.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath mainmap = createBoolean("mainmap");

    public final com.kosa.mapbegood.domain.member.entity.QMember memberEmail;

    public final StringPath memo = createString("memo");

    public final ListPath<com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace, com.kosa.mapbegood.domain.mymap.myplace.entity.QMyplace> myplaceList = this.<com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace, com.kosa.mapbegood.domain.mymap.myplace.entity.QMyplace>createList("myplaceList", com.kosa.mapbegood.domain.mymap.myplace.entity.Myplace.class, com.kosa.mapbegood.domain.mymap.myplace.entity.QMyplace.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final BooleanPath show = createBoolean("show");

    public QThemeMap(String variable) {
        this(ThemeMap.class, forVariable(variable), INITS);
    }

    public QThemeMap(Path<? extends ThemeMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThemeMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThemeMap(PathMetadata metadata, PathInits inits) {
        this(ThemeMap.class, metadata, inits);
    }

    public QThemeMap(Class<? extends ThemeMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEmail = inits.isInitialized("memberEmail") ? new com.kosa.mapbegood.domain.member.entity.QMember(forProperty("memberEmail")) : null;
    }

}

