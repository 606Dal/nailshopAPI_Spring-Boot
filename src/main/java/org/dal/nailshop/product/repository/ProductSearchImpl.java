package org.dal.nailshop.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductListAllDTO;
import org.dal.nailshop.product.dto.ProductListDTO;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.entities.QProductEntity;
import org.dal.nailshop.product.entities.QProductImage;
import org.dal.nailshop.product.entities.QProductReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class ProductSearchImpl implements ProductSearch {

    private final JPQLQueryFactory queryFactory;

    @Override
    public PageResponseDTO<ProductListDTO> productList(PageRequestDTO pageRequestDTO) {
        QProductEntity qProductEntity = QProductEntity.productEntity;
        QProductImage qProductImage = QProductImage.productImage;
        QProductReviewEntity qProductReview = QProductReviewEntity.productReviewEntity;

        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(qProductEntity);
        // 엘리먼트클래스의 조인
        query.leftJoin(qProductEntity.images, qProductImage);
        // 리뷰
        query.leftJoin(qProductReview).on(qProductReview.product.eq(qProductEntity));

        query.where(qProductImage.ord.eq(0));

        // 검색 조건 - 일단 상품 명만
        if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().isEmpty()) {
            query.where(qProductEntity.pname.containsIgnoreCase(pageRequestDTO.getKeyword()));
        }

        // 상품 단위로 처리
        query.groupBy(qProductEntity);

        query.limit(pageRequestDTO.getSize());
        query.offset(pageRequestDTO.getOffset());
        query.orderBy(new OrderSpecifier<>(Order.DESC, qProductEntity.pno));

        JPQLQuery<ProductListDTO> dtoQuery = query.select(Projections.bean(ProductListDTO.class,
                qProductEntity.pno,
                qProductEntity.pname,
                qProductEntity.price,
                qProductImage.imgName.as("imgName"),
                qProductReview.score.coalesce(0).avg().as("avgRating"), // null인 경우 0으로 처리
                qProductReview.count().as("reviewCnt")
        ));

        List<ProductListDTO> dtoList = dtoQuery.fetch();
        long total = dtoQuery.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .total((int) total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public PageResponseDTO<ProductListAllDTO> listAllImages(PageRequestDTO pageRequestDTO) {

        QProductEntity qProductEntity = QProductEntity.productEntity;
        QProductImage qProductImage = QProductImage.productImage;
        QProductReviewEntity qProductReview = QProductReviewEntity.productReviewEntity;

        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(qProductEntity);
        query.leftJoin(qProductReview).on(qProductReview.product.eq(qProductEntity));
        query.leftJoin(qProductEntity.images, qProductImage);

        query.groupBy(qProductEntity);
        query.limit(pageRequestDTO.getLimit());
        query.offset(pageRequestDTO.getOffset());
        query.orderBy(new OrderSpecifier<>(Order.DESC, qProductEntity.pno));

        // countDistinct로 중복된 건 삭제
        JPQLQuery<Tuple> tupleQuery = query.select(
                qProductEntity,
                qProductReview.score.coalesce(0).avg().as("avgRating"),
                qProductReview.countDistinct().as("reviewCnt")
        );

        List<Tuple> tupleList = tupleQuery.fetch();

        List<ProductListAllDTO> dtoList =   tupleList.stream().map(tuple -> {
            ProductEntity product = tuple.get(0, ProductEntity.class);
            List<String> imageNames = product.getImages().stream().map(productImage -> productImage.getImgName()).collect(Collectors.toUnmodifiableList());
            double avgRating = tuple.get(1, double.class);
            long reviewCnt = tuple.get(2, long.class);

            ProductListAllDTO productListAllDTO = new ProductListAllDTO();

            productListAllDTO.setPno(product.getPno());
            productListAllDTO.setPname(product.getPname());
            productListAllDTO.setPrice(product.getPrice());
            productListAllDTO.setImgNames(imageNames);
            productListAllDTO.setAvgRating(avgRating);
            productListAllDTO.setReviewCnt(reviewCnt);

            return productListAllDTO;

        }).collect(Collectors.toUnmodifiableList());

        long total = tupleQuery.fetchCount();

        return PageResponseDTO.<ProductListAllDTO>withAll()
                .dtoList(dtoList)
                .total((int) total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
