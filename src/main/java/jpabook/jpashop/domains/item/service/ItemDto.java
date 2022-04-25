package jpabook.jpashop.domains.item.service;

//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.validator.constraints.Length;
//
//import javax.validation.constraints.Min;
//
//@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor @SuperBuilder
//public class ItemDto {
//
//    @Length(min = 3)
//    private String name;
//    @Length(min = 3)
//    private String imagePath;
//    @Min(0)
//    private int price;
//    @Min(1)
//    private int stockQuantity;
//    private Long categoryId;
//    private ItemType itemType;
//
//    public enum ItemType {
//        ALBUM, BOOK, MOVIE
//    }
//
//    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
//    public static class AlbumDto extends ItemDto {
//
//        private String artist;
//        private String etc;
//    }
//
//    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
//    public static class BookDto extends ItemDto {
//
//        private String author;
//        private String isbn;
//    }
//
//    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
//    public static class MovieDto extends ItemDto {
//
//        private String director;
//        private String actor;
//
//    }
//
//}
