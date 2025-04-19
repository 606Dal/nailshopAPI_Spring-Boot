package org.dal.nailshop.product.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = -1689902403L;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final org.dal.nailshop.todo.entities.QBaseEntity _super = new org.dal.nailshop.todo.entities.QBaseEntity(this);

    public final ListPath<ProductImage, QProductImage> images = this.<ProductImage, QProductImage>createList("images", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath pname = createString("pname");

    public final NumberPath<Long> pno = createNumber("pno", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }

}

