package org.dal.nailshop.product.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductModifyDTO {

    private Long pno;

    private String pname;
    private String pdesc;

    private int price;

    private List<String> imageNames = new ArrayList<>();

    private List<MultipartFile> files; // 업로드할 이미지 파일들
}
