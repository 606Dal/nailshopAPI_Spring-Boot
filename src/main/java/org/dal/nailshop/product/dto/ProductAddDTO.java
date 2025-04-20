package org.dal.nailshop.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductAddDTO {

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String pname;

    @NotBlank(message = "상품 설명은 필수입니다.")
    private String pdesc;

    @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
    private int price;

    private List<String> imageNames = new ArrayList<>();

    @NotEmpty(message = "이미지는 최소 한 개 이상 업로드해야 합니다.")
    private List<MultipartFile> files; // 업로드할 이미지 파일들
}
