package com.kosa.mapbegood.domain.ourmap.ourplaceFeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOurplaceFeedEmbedded is a Querydsl query type for OurplaceFeedEmbedded
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOurplaceFeedEmbedded extends BeanPath<OurplaceFeedEmbedded> {

    private static final long serialVersionUID = 4404558L;

    public static final QOurplaceFeedEmbedded ourplaceFeedEmbedded = new QOurplaceFeedEmbedded("ourplaceFeedEmbedded");

    public final StringPath email = createString("email");

    public final NumberPath<Long> ourplaceId = createNumber("ourplaceId", Long.class);

    public QOurplaceFeedEmbedded(String variable) {
        super(OurplaceFeedEmbedded.class, forVariable(variable));
    }

    public QOurplaceFeedEmbedded(Path<? extends OurplaceFeedEmbedded> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOurplaceFeedEmbedded(PathMetadata metadata) {
        super(OurplaceFeedEmbedded.class, metadata);
    }

}

