package org.dal.nailshop.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.common.dto.PageRequestDTO;
import org.dal.nailshop.common.dto.PageResponseDTO;
import org.dal.nailshop.product.dto.*;
import org.dal.nailshop.product.service.ProductReviewService;
import org.dal.nailshop.product.service.ProductService;
import org.dal.nailshop.todo.dto.ActionResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    private final ProductReviewService reviewService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(PageRequestDTO requestDTO) {

        PageResponseDTO<ProductListDTO> list = service.listProducts(requestDTO);

        return ResponseEntity.ok(list);
    }

    @GetMapping("listall")
    public ResponseEntity<PageResponseDTO<ProductListAllDTO>> listAll(PageRequestDTO requestDTO) {

        PageResponseDTO<ProductListAllDTO> listAll = service.listProductsWithAllImages(requestDTO);

        return ResponseEntity.ok(listAll);
    }

    @PostMapping("add")
    public ResponseEntity<ActionResultDTO<Long>> add(@ModelAttribute @Valid ProductAddDTO dto) {

        log.info("===========post==============");
        log.info(dto.toString());

        Long pno = service.add(dto);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Add Success")
                .data(pno)
                .build());
    }

    @GetMapping("/{pno}")
    public ResponseEntity<ProductReadDTO> read(@PathVariable Long pno) {

        ProductReadDTO dto = service.read(pno);

        if (dto != null) {
            return ResponseEntity.ok(dto); // 200 OK + body에 dto 담김
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("modify/{pno}")
    public ResponseEntity<ActionResultDTO<Long>> modify(@ModelAttribute @Valid ProductModifyDTO dto) {

        log.info("imageNames in controller: {}", dto.getImageNames());

        service.modify(dto);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Update Success")
                .data(dto.getPno())
                .build()
        );
    }

    @GetMapping("{pno}/reviews")
    public ResponseEntity<PageResponseDTO<ProductReviewListDTO>> reviewList(@PathVariable Long pno, PageRequestDTO requestDTO) {

        return ResponseEntity.ok(reviewService.reviewList(pno, requestDTO));
    }

}
