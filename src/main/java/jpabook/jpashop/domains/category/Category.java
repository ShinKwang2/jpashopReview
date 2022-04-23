package jpabook.jpashop.domains.category;

import jpabook.jpashop.domains.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    /**
     * 셀프 양방향 연관관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(String categoryName, Category parent) {
        this.categoryName = categoryName;
        this.parent = parent;
    }

    /**
     * 셀프 양방향 연관관계 편의 메서드
     */
    public void addParentCategory(Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

    public void addCategoryItem(CategoryItem... categoryItemList) {
        Arrays.stream(categoryItemList)
                .forEach(categoryItem -> categoryItems.add(categoryItem));

        Arrays.stream(categoryItemList)
                .forEach(categoryItem -> categoryItem.addCategory(this));
    }


}
