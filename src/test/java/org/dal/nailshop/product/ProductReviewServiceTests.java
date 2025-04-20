package org.dal.nailshop.product;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductReviewAddDTO;
import org.dal.nailshop.product.dto.ProductReviewListDTO;
import org.dal.nailshop.product.dto.ProductReviewReadDTO;
import org.dal.nailshop.product.service.ProductReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ProductReviewServiceTests {

    @Autowired
    private ProductReviewService service;

    @Test
    public void testReviewList() {

        Long pno = 30L;

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductReviewListDTO> result = service.reviewList(pno, requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });
    }

    @Test
    public void testReadReview() {
        Long rno = 31L;

        ProductReviewReadDTO dto = service.readReview(rno);
        log.info(dto);
    }

//    @Test
//    public void testAddReview() {
//        Long pno = 7L;
//
//        ProductReviewAddDTO dto = new ProductReviewAddDTO();
//        dto.setReviewer("user7");
//        dto.setScore(5);
//        dto.setComment("굿굿");
//        dto.setImageNames(List.of("aaa.jpg","bbb.jpg"));
//        dto.setPno(pno);
//
//        Long rno = service.add(dto);
//        log.info(rno);
//    }

}
