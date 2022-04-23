package jpabook.jpashop.domains.item;

import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.category.CategoryItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private int price;  // 가격
    private int stockQuantity;  // 재고수량

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    Item(String itemName, int price, int stockQuantity) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //==양방향 편의 메서드==//
    public void addCategoryItem(CategoryItem... categoryItemList) {
        Arrays.stream(categoryItemList)
                .forEach(categoryItem -> categoryItems.add(categoryItem));
        Arrays.stream(categoryItemList)
                .forEach(categoryItem -> categoryItem.addItem(this));
    }
}
