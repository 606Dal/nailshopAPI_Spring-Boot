package org.dal.nailshop.product.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@Setter
public class ProductReviewImage {

    private String imgName;

    private int ord;
}
