package org.dal.nailshop.product.repository;

import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Long> {

//    @EntityGraph(attributePaths = "product")
//    @Query("select pr from ProductReviewEntity pr where pr.product.pno = :pno")
//    List<ProductReviewEntity> findByProductPno(@Param("pno") Long pno);
}
