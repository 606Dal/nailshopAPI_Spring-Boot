package org.dal.nailshop.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dal.nailshop.product.entities.ProductReviewEntity;
import org.dal.nailshop.product.entities.ProductReviewImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductReviewListDTO {

    private Long rno;

    private String reviewer;

    private int score;

    private String comment;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<String> imgNames;

    private Long pno; // 조인 필요

    public ProductReviewListDTO(ProductReviewEntity reviewEntity) {
        this.rno = reviewEntity.getRno();
        this.reviewer = reviewEntity.getReviewer();
        this.comment = reviewEntity.getComment();
        this.score = reviewEntity.getScore();
        this.regDate = reviewEntity.getRegDate();
        this.modDate = reviewEntity.getModDate();
        this.pno = reviewEntity.getProduct().getPno();

        this.imgNames = reviewEntity.getImages()
                .stream()
                .map(ProductReviewImage::getImgName) // 혹은 uuid + fileName 조합 등
                .collect(Collectors.toList());
    }
}
