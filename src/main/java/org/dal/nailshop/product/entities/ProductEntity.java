package org.dal.nailshop.product.entities;

import jakarta.persistence.*;
import lombok.*;
import org.dal.nailshop.todo.entities.BaseEntity;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@ToString(exclude = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tbl_product_img",
            joinColumns = @JoinColumn(name="product_pno"))
    @BatchSize(size = 20)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    // UUID 비슷
    public void addImage(String fileName) {
        ProductImage image = new ProductImage();
        image.setImgName(fileName);
        image.setOrd(images.size()); // 이미지 순서 0부터

        images.add(image);
    }

    // 삭제 할때는 배열을 새로 만들지 않고 내용물만 비울 예정
    public void clearImages(){
        images.clear();
    }

    public void changePname(String pname) {
        this.pname = pname;
    }

    public void changePrice(int price) {
        this.price = price;
    }
}
