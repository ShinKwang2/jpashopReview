package jpabook.jpashop.domains;

import jpabook.jpashop.domains.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CategoryItem {

    @Id @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public CategoryItem(Category category, Item item) {
        changeCategory(category);   //양방향 편의메서드
        changeItem(item);           //양방향 편의메서드
    }

    // 양방향 편의 메서드 - Category
    private void changeCategory(Category category) {
        this.category = category;
        category.getCategoryItems().add(this);
    }
    // 양방향 편의 메서드 - Item
    private void changeItem(Item item) {
        this.item = item;
        item.getCategoryItems().add(this);
    }
}
