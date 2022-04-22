package jpabook.jpashop.domains;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Builder

    public Category(String categoryName, List<CategoryItem> categoryItems) {
        this.categoryName = categoryName;
        this.categoryItems = categoryItems;
    }

    /**
     * 셀프 양방향 연관관계 편의 메서드
     */
    private void changeChild(Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }


}
