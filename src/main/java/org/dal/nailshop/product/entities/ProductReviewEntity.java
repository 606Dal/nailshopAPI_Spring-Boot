package org.dal.nailshop.product.entities;

import jakarta.persistence.*;
import lombok.*;
import org.dal.nailshop.todo.entities.BaseEntity;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product_review")
@Getter
@ToString(exclude = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    private String comment;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tbl_review_img",
            joinColumns = @JoinColumn(name="product_review_rno"))
    @BatchSize(size = 20)
    @Builder.Default
    private List<ProductReviewImage> images = new ArrayList<>();

    public void addImage(String fileName) {
        ProductReviewImage image = new ProductReviewImage();
        image.setImgName(fileName);
        image.setOrd(images.size()); // 이미지 순서 0부터

        images.add(image);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;


    public void changeScore(int score) {
        this.score = score;
    }
    public void changeComment(String comment) {
        this.comment = comment;
    }

    public void clearImages(){
        images.clear();
    }
}
