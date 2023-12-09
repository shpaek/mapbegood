package com.kosa.mapbegood.domain.mymap.favorite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavoriteEmbedded is a Querydsl query type for FavoriteEmbedded
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteEmbedded extends BeanPath<FavoriteEmbedded> {

    private static final long serialVersionUID = 2006876838L;

    public static final QFavoriteEmbedded favoriteEmbedded = new QFavoriteEmbedded("favoriteEmbedded");

    public final StringPath email = createString("email");

    public final NumberPath<Long> thememapId = createNumber("thememapId", Long.class);

    public QFavoriteEmbedded(String variable) {
        super(FavoriteEmbedded.class, forVariable(variable));
    }

    public QFavoriteEmbedded(Path<? extends FavoriteEmbedded> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavoriteEmbedded(PathMetadata metadata) {
        super(FavoriteEmbedded.class, metadata);
    }

}

