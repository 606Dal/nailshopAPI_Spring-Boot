package org.dal.nailshop.product.repository;

import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductListAllDTO;
import org.dal.nailshop.product.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> productList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListAllDTO> listAllImages(PageRequestDTO pageRequestDTO);

}
