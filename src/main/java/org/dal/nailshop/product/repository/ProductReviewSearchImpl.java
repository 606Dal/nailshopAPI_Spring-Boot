package org.dal.nailshop.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductReviewListDTO;
import org.dal.nailshop.product.entities.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class ProductReviewSearchImpl implements ProductReviewSearch {

    private final JPQLQueryFactory queryFactory;

    @Override
    public PageResponseDTO<ProductReviewListDTO> reviewList(Long pno, PageRequestDTO pageRequestDTO) {

        QProductReviewEntity qReviewEntity = QProductReviewEntity.productReviewEntity;
        QProductReviewImage qReviewImage = QProductReviewImage.productReviewImage;

        JPQLQuery<ProductReviewEntity> query = queryFactory.selectFrom(qReviewEntity);
        query.leftJoin(qReviewEntity.images, qReviewImage);

        query.where(qReviewEntity.product.pno.eq(pno));

        // 검색 조건 (일단 제외)

        query.groupBy(qReviewEntity);
        query.limit(pageRequestDTO.getLimit());
        query.offset(pageRequestDTO.getOffset());
        query.orderBy(new OrderSpecifier<>(Order.DESC, qReviewEntity.rno));

        List<ProductReviewEntity> resultList = query.fetch();

        List<ProductReviewListDTO> dtoList = resultList.stream()
                .map(ProductReviewListDTO::new)
                .collect(Collectors.toUnmodifiableList());

        long total = query.fetchCount();

        return PageResponseDTO.<ProductReviewListDTO>withAll()
                .dtoList(dtoList)
                .total((int) total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
