package org.dal.nailshop.product;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.dal.nailshop.product.repository.ProductRepository;
import org.dal.nailshop.product.repository.ProductReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@Transactional
public class ProductReviewRepoTests {

    @Autowired(required = false)
    ProductReviewRepository reviewRepo;

    @Test
//    @Commit
    public void insertReviews() {

        Long[] pnos  = {7L, 30L, 27L};

        for (Long pno : pnos) {

            for (int i = 0; i < 10; i++) {

                ProductReviewEntity pr = ProductReviewEntity.builder()
                        .reviewer("user1")
                        .comment("Good")
                        .score( (i % 5) +1 )
                        .product(ProductEntity.builder().pno(pno).build())
                        .build();

                pr.addImage(i + "_img0.jpg");
                pr.addImage(i + "_img1.jpg");

                reviewRepo.save(pr);

            }//for inner

        }//for outer
    }

    @Test
    public void reviewList() {
        Long pno = 7L;

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setSize(10);

        PageResponseDTO<ProductReviewListDTO> result = reviewRepo.reviewList(pno, requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });

//        Long pno = 7L;
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
//
//        Page<ProductReviewListDTO> result = reviewRepo.findByProductPno(pno, pageable);
//
//        result.getContent().forEach(dto -> log.info(dto));
    }

    @Test
    public void testRead(){
        Long rno = 31L;

        ProductReviewReadDTO dto = reviewRepo.selectOne(rno);

        log.info(dto);
    }

    @Test
    public void testFindById(){
        Long rno = 31L;

        Optional<ProductReviewEntity> entity = reviewRepo.findById(rno);

        log.info(entity);
    }

    @Test
    public void testDelete() {
        Long rno = 32L;

        reviewRepo.deleteById(rno);
    }


}
