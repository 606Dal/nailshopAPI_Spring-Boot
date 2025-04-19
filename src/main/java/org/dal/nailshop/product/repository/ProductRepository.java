package org.dal.nailshop.product.repository;

import org.dal.nailshop.product.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {

    @EntityGraph(attributePaths = "images", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select p from ProductEntity p where p.pno = :pno ")
    ProductEntity selectOne(@Param("pno") Long pno);

//    @Query("select p.pno, p.pname, p.price, p.regDate, p.modDate, pi.imgName " +
//            "from ProductEntity p left join p.images pi WHERE pi.ord = 0 ")
//    Page<Object[]> list(Pageable pageable);
}
