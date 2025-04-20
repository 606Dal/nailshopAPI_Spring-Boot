package org.dal.nailshop.product.repository;

import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.ProductReviewListDTO;

public interface ProductReviewSearch {

    PageResponseDTO<ProductReviewListDTO> reviewList(Long pno, PageRequestDTO pageRequestDTO);

}
