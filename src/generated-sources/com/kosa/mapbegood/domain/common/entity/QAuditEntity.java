package com.kosa.mapbegood.domain.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditEntity is a Querydsl query type for AuditEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditEntity extends EntityPathBase<AuditEntity> {

    private static final long serialVersionUID = -1193663411L;

    public static final QAuditEntity auditEntity = new QAuditEntity("auditEntity");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public QAuditEntity(String variable) {
        super(AuditEntity.class, forVariable(variable));
    }

    public QAuditEntity(Path<? extends AuditEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditEntity(PathMetadata metadata) {
        super(AuditEntity.class, metadata);
    }

}

