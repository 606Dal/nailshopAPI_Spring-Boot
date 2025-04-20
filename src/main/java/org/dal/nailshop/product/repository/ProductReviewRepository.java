package org.dal.nailshop.product.repository;

import org.dal.nailshop.product.dto.ProductReviewListDTO;
import org.dal.nailshop.product.dto.ProductReviewReadDTO;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Long>, ProductReviewSearch {

    @Query("select new org.dal.nailshop.product.dto.ProductReviewListDTO(pr) from ProductReviewEntity pr where pr.product.pno = :pno ")
    Page<ProductReviewListDTO> findByProductPno(@Param("pno") Long pno, Pageable pageable);

    @Query("select new org.dal.nailshop.product.dto.ProductReviewReadDTO(pr) from ProductReviewEntity pr where pr.rno = :rno ")
    ProductReviewReadDTO selectOne(@Param("rno") Long rno);

}
