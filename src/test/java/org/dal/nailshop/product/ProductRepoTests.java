package org.dal.nailshop.product;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductListAllDTO;
import org.dal.nailshop.product.dto.ProductListDTO;
import org.dal.nailshop.product.dto.ProductReadDTO;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.dal.nailshop.product.repository.ProductRepository;
import org.dal.nailshop.product.repository.ProductReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@Transactional
public class ProductRepoTests {

    @Autowired(required = false)
    ProductRepository repo;

    @Autowired(required = false)
    ProductReviewRepository reviewRepo;


    @Test
//    @Commit
    public void insertProduct() {

        for (int i = 0; i < 30; i++) {
            ProductEntity product = ProductEntity.builder()
                    .pname("Product" + i )
                    .price(5000)
                    .build();
            product.addImage(i + "_img0.jpg");
            product.addImage(i + "_img1.jpg");

            repo.save(product);
        } // end for
    }

    @Test
    public void readProduct() {

        ProductEntity product = repo.selectOne(1L);
        ProductReadDTO dto = new ProductReadDTO(product);

        log.info(dto);
    }

    @Test
//    @Commit
    public void insertReviews() {

        Long[] pnos  = {7L, 30L, 27L};

        for (Long pno : pnos) {

            for (int i = 0; i < 10; i++) {

                ProductReviewEntity pr = ProductReviewEntity.builder()
                        .reviewer("user00")
                        .comment("Good")
                        .score( (i % 5) +1 )
                        .product(ProductEntity.builder().pno(pno).build())
                        .build();

                reviewRepo.save(pr);

            }//for inner

        }//for outer
    }

//    @Test
//    public void testList() {
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
//
//        Page<Object[]> result = repo.list(pageable);
//
//        result.forEach(arr -> log.info(Arrays.toString(arr)));
//
//    }

    @Test
    public void testProductSearchList() {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setSize(10);
        requestDTO.setKeyword("6");

        PageResponseDTO<ProductListDTO> result = repo.productList(requestDTO);

        log.info(result);
        result.getDtoList().forEach(dto -> log.info(dto));
    }

    @Test
    public void testAllList() {

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListAllDTO> result = repo.listAllImages(requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });

    }

    @Test
    public void testUpdateProduct() {

        ProductEntity product = repo.selectOne(1L);

        product.changePname("Updated Product");
        product.changePrice(7000);
        product.clearImages(); // 기존 이미지 제거
        product.addImage("11_1.jpg");
        product.addImage("11_2.jpg");

        repo.save(product);

        // 검증
        ProductEntity updated = repo.selectOne(1L);

        assertThat(updated.getPname()).isEqualTo("Updated Product");
        assertThat(updated.getPrice()).isEqualTo(7000);
        assertThat(updated.getImages()).hasSize(2);
        assertThat(updated.getImages().get(0).getImgName()).isEqualTo("11_1.jpg");
        assertThat(updated.getImages().get(1).getImgName()).isEqualTo("11_2.jpg");

    }

    @Test
    public void testDeleteProduct() {
        Long pno = 4L;

        repo.deleteById(pno);

        Optional<ProductEntity> result = repo.findById(pno);
        assertThat(result).isEmpty();
    }



}
