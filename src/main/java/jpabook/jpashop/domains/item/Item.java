package jpabook.jpashop.domains.item;

import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.item.service.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String imagePath;   // galid1 참조

    private String itemName;
    private int price;  // 가격
    private int stockQuantity;  // 재고수량

    private Long categoryId;

    @Builder
    public Item(String imagePath, String itemName, int price, int stockQuantity, Long categoryId) {
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }

    //== 비즈니스 로직 ==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소 / 0 보다 줄어선 안되니 check메소드 추가
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }
}
