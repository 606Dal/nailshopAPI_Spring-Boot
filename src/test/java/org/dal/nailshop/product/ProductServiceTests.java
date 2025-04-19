package org.dal.nailshop.product;

import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.product.dto.ProductAddDTO;
import org.dal.nailshop.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Test
    public void testAddProduct() {

        ProductAddDTO dto = new ProductAddDTO();
        dto.setPname("Sample Product");
        dto.setPrice(8000);
        dto.setImageNames(List.of("aaa.jpg","bbb.jpg","ccc.jpg"));

        Long pno = service.add(dto);

        log.info(pno);
    }

}
