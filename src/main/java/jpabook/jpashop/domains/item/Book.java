package jpabook.jpashop.domains.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Book extends Item {
//
//    private String author;
//    private String isbn;
//
//    @Builder
//    public Book(String imagePath, String itemName, int price, int stockQuantity, Long categoryId, String author, String isbn) {
//        super(imagePath, itemName, price, stockQuantity, categoryId);
//        this.author = author;
//        this.isbn = isbn;
//    }
//}
