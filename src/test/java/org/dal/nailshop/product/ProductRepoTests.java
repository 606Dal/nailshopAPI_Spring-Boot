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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@Transactional
public class ProductRepoTests {

    @Autowired(required = false)
    ProductRepository repo;

    @Test
//    @Commit
    public void insertProduct() {

        for (int i = 0; i < 2; i++) {
            ProductEntity product = ProductEntity.builder()
                    .pname("Product" + i )
                    .pdesc("tsetDesc")
                    .price(7000)
                    .build();
            product.addImage(i + "_img0.jpg");
            product.addImage(i + "_img1.jpg");

            repo.save(product);
        } // end for
    }

    @Test
    public void readProduct() {

        ProductEntity product = repo.selectOne(32L);
        ProductReadDTO dto = new ProductReadDTO(product);

        log.info(dto);
    }

    @Test
    public void testProductSearchList() {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setSize(10);
        requestDTO.setType("PRICE");
        requestDTO.setKeyword("7000");

        PageResponseDTO<ProductListDTO> result = repo.productList(requestDTO);

        log.info(result);
        result.getDtoList().forEach(dto -> log.info(dto));
    }

    @Test
    public void testAllList() {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setSize(10);
        requestDTO.setType("PNAME");
        requestDTO.setKeyword("1");

        PageResponseDTO<ProductListAllDTO> result = repo.listAllImages(requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });
    }

    @Test
    public void testUpdateProduct() {

        ProductEntity product = repo.selectOne(2L);

        product.changePname("Updated Product");
        product.changePdesc("정보 수정");
        product.changePrice(7000);
        product.clearImages(); // 기존 이미지 제거
        product.addImage("11_1.jpg");
        product.addImage("11_2.jpg");

        repo.save(product);

        // 검증
        ProductEntity updated = repo.selectOne(2L);

        assertThat(updated.getPname()).isEqualTo("Updated Product");
        assertThat(updated.getPdesc()).isEqualTo("정보 수정");
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
