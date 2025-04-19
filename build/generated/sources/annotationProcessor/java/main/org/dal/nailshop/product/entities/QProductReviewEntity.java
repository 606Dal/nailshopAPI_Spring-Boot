package org.dal.nailshop.product.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReviewEntity is a Querydsl query type for ProductReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReviewEntity extends EntityPathBase<ProductReviewEntity> {

    private static final long serialVersionUID = 1026627765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReviewEntity productReviewEntity = new QProductReviewEntity("productReviewEntity");

    public final StringPath comment = createString("comment");

    public final QProductEntity product;

    public final StringPath reviewer = createString("reviewer");

    public final NumberPath<Long> rno = createNumber("rno", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QProductReviewEntity(String variable) {
        this(ProductReviewEntity.class, forVariable(variable), INITS);
    }

    public QProductReviewEntity(Path<? extends ProductReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReviewEntity(PathMetadata metadata, PathInits inits) {
        this(ProductReviewEntity.class, metadata, inits);
    }

    public QProductReviewEntity(Class<? extends ProductReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProductEntity(forProperty("product")) : null;
    }

}

