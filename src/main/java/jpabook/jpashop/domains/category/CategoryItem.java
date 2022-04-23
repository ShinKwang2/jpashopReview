package jpabook.jpashop.domains.category;

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
        this.category = category;
        this.item = item;
    }

    //==양방향 편의 메서드==//
    public void addCategory(Category category) {
        this.category = category;
    }
    public void addItem(Item item) {
        this.item = item;
    }
}
