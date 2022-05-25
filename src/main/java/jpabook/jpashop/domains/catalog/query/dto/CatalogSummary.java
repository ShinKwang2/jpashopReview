package jpabook.jpashop.domains.catalog.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatalogSummary {

    private Long itemId;
    private String imagePath;
    private String name;
    private Integer price;

    @QueryProjection
    public CatalogSummary(Long itemId, String imagePath, String name, Integer price) {
        this.itemId = itemId;
        this.imagePath = imagePath;
        this.name = name;
        this.price = price;
    }
}
