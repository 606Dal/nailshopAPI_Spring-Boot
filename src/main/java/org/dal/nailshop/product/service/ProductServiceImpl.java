package org.dal.nailshop.product.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.product.dto.ProductAddDTO;
import org.dal.nailshop.product.entities.ProductEntity;
import org.dal.nailshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Long add(ProductAddDTO dto) {
        ProductEntity productEntity = addDTOToEntity(dto);
        repository.save(productEntity);

        return productEntity.getPno();
    }
}
