package org.dal.nailshop.product.service;

import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.entities.ProductEntity;

public interface ProductService {

    Long add(ProductAddDTO dto);

    ProductReadDTO read(Long pno);

    PageResponseDTO<ProductListDTO> listProducts(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListAllDTO> listProductsWithAllImages(PageRequestDTO pageRequestDTO);

    void modify(ProductModifyDTO dto);


    default ProductEntity addDTOToEntity(ProductAddDTO dto) {
        ProductEntity entity = ProductEntity.builder()
                .pname(dto.getPname())
                .pdesc(dto.getPdesc())
                .price(dto.getPrice())
                .build();

        dto.getImageNames().forEach(imgName -> entity.addImage(imgName));

        return entity;
    }
}
