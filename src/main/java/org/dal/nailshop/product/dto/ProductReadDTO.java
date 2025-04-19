package org.dal.nailshop.product.dto;

import lombok.Data;
import org.dal.nailshop.product.entities.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductReadDTO {

    private Long pno;

    private String pname;

    private int price;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private List<String> imageNames;

    public ProductReadDTO(ProductEntity entity) {
        this.pno = entity.getPno();
        this.pname = entity.getPname();
        this.price = entity.getPrice();
        this.regDate = entity.getRegDate();
        this.modDate = entity.getModDate();
        this.imageNames = entity.getImages().stream().map(pi -> pi.getImgName()).collect(Collectors.toList());
    }
}
