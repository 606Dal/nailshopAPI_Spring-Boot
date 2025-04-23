package org.dal.nailshop.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductReviewModifyDTO {

    @NotNull(message = "리뷰 번호는 필수입니다.")
    private Long rno;

    @Range(min = 1, max = 5, message = "점수는 1~5 사이의 숫자만 가능합니다.")
    private int score;

    @NotBlank(message = "내용은 필수입니다.")
    private String comment;

    private List<String> imageNames = new ArrayList<>();

    private List<MultipartFile> files; // 업로드할 이미지 파일들

}
