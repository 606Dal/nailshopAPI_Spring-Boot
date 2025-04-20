package org.dal.nailshop.product.service;

import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReviewService {

    Page<ProductReviewListDTO> getListOfProduct (Long pno, Pageable pageable);

    PageResponseDTO<ProductReviewListDTO> reviewList(Long pno, PageRequestDTO pageRequestDTO);

    ProductReviewReadDTO readReview(Long rno);

    Long add(ProductReviewAddDTO dto);

    void modify(ProductReviewModifyDTO dto);

    default ProductReviewEntity addDTOToEntity(ProductReviewAddDTO dto) {
        ProductReviewEntity entity = ProductReviewEntity.builder()
                .reviewer(dto.getReviewer())
                .score(dto.getScore())
                .comment(dto.getComment())
                .product(ProductEntity.builder().pno(dto.getPno()).build())
                .build();

        dto.getImageNames().forEach(imgName -> entity.addImage(imgName));

        return entity;
    }
}
