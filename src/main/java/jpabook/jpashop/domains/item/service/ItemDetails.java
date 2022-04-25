package jpabook.jpashop.domains.item.service;

import jpabook.jpashop.domains.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Service
public class ItemDetails {

    private Long itemId;
    private String imagePath;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemDetails(Item item) {
        this.itemId = item.getId();
        this.imagePath = item.getImagePath();
        this.name = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
