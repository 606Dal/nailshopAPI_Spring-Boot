package org.dal.nailshop.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.dal.nailshop.product.entities.ProductReviewImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductReviewReadDTO {

    private Long rno;

    private String reviewer;

    private int score;

    private String comment;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<String> imgNames;

    private Long pno; // 조인 필요

    public ProductReviewReadDTO(ProductReviewEntity reviewEntity) {
        this.rno = reviewEntity.getRno();
        this.reviewer = reviewEntity.getReviewer();
        this.comment = reviewEntity.getComment();
        this.score = reviewEntity.getScore();
        this.regDate = reviewEntity.getRegDate();
        this.modDate = reviewEntity.getModDate();
        this.pno = reviewEntity.getProduct().getPno();

        this.imgNames = reviewEntity.getImages()
                .stream()
                .map(ProductReviewImage::getImgName)
                .collect(Collectors.toList());
    }
}
