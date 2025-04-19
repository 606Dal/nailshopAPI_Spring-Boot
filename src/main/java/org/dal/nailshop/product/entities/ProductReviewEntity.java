package org.dal.nailshop.product.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_product_review")
@Getter
@ToString(exclude = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;
}
