package org.dal.nailshop.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dal.nailshop.product.dto.ProductModifyDTO;
import org.dal.nailshop.product.dto.ProductReviewAddDTO;
import org.dal.nailshop.product.dto.ProductReviewModifyDTO;
import org.dal.nailshop.product.service.ProductReviewService;
import org.dal.nailshop.todo.dto.ActionResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@Log4j2
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ActionResultDTO<Long>> add(@ModelAttribute ProductReviewAddDTO dto) {

        log.info("===========post review==============");
        log.info(dto.toString());

        Long rno = reviewService.add(dto);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Review Add Success")
                .data(rno)
                .build());
    }

    @PutMapping("modify/{rno}")
    public ResponseEntity<ActionResultDTO<Long>> modify(@ModelAttribute ProductReviewModifyDTO dto) {

        log.info("imageNames in controller: {}", dto.getImageNames());

        reviewService.modify(dto);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                .result("Update Success")
                .data(dto.getRno())
                .build()
        );
    }
}
