package org.dal.nailshop.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProductAddDTO {

    @NotBlank
    private String pname;

    private int price;

    private List<String> imageNames;
}
