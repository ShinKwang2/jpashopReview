package jpabook.jpashop.domains.item.service;

import jpabook.jpashop.domains.item.Item;
import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemRequest {

    private String name;
    private String imagePath;
    @Min(0)
    private int price;
    @Min(1)
    private int stockQuantity;
    private Long categoryId;

    public Item toEntity() {
        return Item.builder()
                .itemName(name)
                .imagePath(imagePath)
                .price(price)
                .stockQuantity(stockQuantity)
                .categoryId(categoryId)
                .build();
    }
}
