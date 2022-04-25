package jpabook.jpashop.domains.item;

import jpabook.jpashop.domains.item.service.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ItemTest {

    @Test
    void 주문시_재고량_차감() throws Exception {
        //given
        int STOCK_QUANTITY = 10;
        Item itemEntity = createItemEntity(STOCK_QUANTITY);

        //when
        int ORDER_QUANTITY = 2;
        itemEntity.removeStock(ORDER_QUANTITY);

        //then
        assertThat(itemEntity.getStockQuantity()).isEqualTo(STOCK_QUANTITY - ORDER_QUANTITY);
     }

     @Test
     void 재고량_초과주문시_에러() throws Exception {
         //given
         int STOCK_QUANTITY = 10;
         Item itemEntity = createItemEntity(STOCK_QUANTITY);

         //when
         int OVER_ORDER_QUANTITY = 20;

         //then
         assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(() -> itemEntity.removeStock(OVER_ORDER_QUANTITY));
         assertThatThrownBy(() -> itemEntity.removeStock(OVER_ORDER_QUANTITY));

      }

    private Item createItemEntity(int stockQuantity) {
        Item itemEntity = Item.builder()
                .categoryId(1L)
                .imagePath("Test")
                .itemName("JPABook")
                .price(1000)
                .stockQuantity(stockQuantity)
                .build();
        return itemEntity;
    }

    @Test
    void 재고량_추가() throws Exception {
        //given
        int STOCK_QUANTITY = 10;
        Item itemEntity = createItemEntity(STOCK_QUANTITY);

        //when
        int ADD_STOCK_QUANTITY = 10;
        itemEntity.addStock(ADD_STOCK_QUANTITY);

        //then
        assertThat(itemEntity.getStockQuantity()).isEqualTo(STOCK_QUANTITY + ADD_STOCK_QUANTITY);

     }

}