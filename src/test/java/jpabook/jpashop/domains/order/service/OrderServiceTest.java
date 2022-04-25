package jpabook.jpashop.domains.order.service;

import jpabook.jpashop.domains.category.Category;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import jpabook.jpashop.domains.member.service.MemberService;
import jpabook.jpashop.domains.member.service.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemRepository itemRepository;

    

    @Test
    void 주문_재고감소_확인() throws Exception {
        //given
        SignUpRequest memberRequest = SignUpRequest.builder()
                .authId("sklee0206")
                .password("1234")
                .name("LEE")
                .phone("01099999999")
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .build();
        Long savedMemberId = memberService.signUp(memberRequest);

        Item item = Item.builder()
                .imagePath("test")
                .itemName("JPA북")
                .price(1000)
                .stockQuantity(100)
                .categoryId(10L)
                .build();
        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem.getStockQuantity() = " + savedItem.getStockQuantity());
        //when
        OrderItemRequest orderItemRequest = new OrderItemRequest(
                savedItem.getId(),
                savedItem.getPrice(),
                10);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.getOrderItemList().add(orderItemRequest);
        System.out.println("=====================");

        Long savedOrderId = orderService.order(savedMemberId, orderRequest);

        //then
        System.out.println("item.getStockQuantity() = " + item.getStockQuantity());

        
     }
}