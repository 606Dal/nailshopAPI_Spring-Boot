package org.dal.nailshop.product.service;

import org.dal.nailshop.product.dto.ProductAddDTO;
import org.dal.nailshop.product.entities.ProductEntity;

public interface ProductService {

    Long add(ProductAddDTO dto);

    default ProductEntity addDTOToEntity(ProductAddDTO dto) {
        ProductEntity entity = ProductEntity.builder()
                .pname(dto.getPname())
                .price(dto.getPrice())
                .build();

        dto.getImageNames().forEach(imgName -> entity.addImage(imgName));

        return entity;
    }
}
