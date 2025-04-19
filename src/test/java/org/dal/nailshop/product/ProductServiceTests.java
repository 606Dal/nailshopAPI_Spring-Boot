package org.dal.nailshop.product;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.service.ProductService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Disabled
    @Test
    public void testAddProduct() {

        ProductAddDTO dto = new ProductAddDTO();
        dto.setPname("Sample Product");
        dto.setPrice(8000);
        dto.setImageNames(List.of("aaa.jpg","bbb.jpg","ccc.jpg"));

        Long pno = service.add(dto);

        log.info(pno);
    }

    @Test
    public void testReadProduct() {

        ProductReadDTO dto = service.read(30L);
        log.info(dto);
    }

    @Test
    public void testListProduct() {

        PageRequestDTO requestDTO = new PageRequestDTO();
//        requestDTO.setPage(2);
//        requestDTO.setSize(15);
        requestDTO.setKeyword("6");

        PageResponseDTO<ProductListDTO> result = service.listProducts(requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });
    }

    @Test
    public void testListAllProduct() {

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListAllDTO> result = service.listProductsWithAllImages(requestDTO);

        result.getDtoList().forEach(dto -> {
            log.info(dto);
        });
    }

    @Test
    @Commit
    public void testModifyProduct() {

        ProductModifyDTO dto = new ProductModifyDTO();
        dto.setPno(2L);
        dto.setPname("Sample No2 Product");
        dto.setPrice(7000);
        dto.setImageNames(List.of("2_a.jpg","2_b.jpg","2_c.jpg"));

        service.modify(dto);
    }

}
