package org.dal.nailshop.product.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@Setter
public class ProductImage {

    private String imgName;

    private int ord; // 이미지 순서
}
