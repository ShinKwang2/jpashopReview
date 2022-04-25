package jpabook.jpashop.domains.item.service;

import jpabook.jpashop.domains.item.Item;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AddItemRequest {

    @Length(min = 3)
    private String name;
    @Length(min = 3)
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
