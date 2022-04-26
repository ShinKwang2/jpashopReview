package jpabook.jpashop.domains.order.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.delivery.Delivery;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import jpabook.jpashop.domains.item.service.ItemService;
import jpabook.jpashop.domains.item.service.NotEnoughStockException;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.service.MemberService;
import jpabook.jpashop.domains.order.Order;
import jpabook.jpashop.domains.order.OrderItem;
import jpabook.jpashop.domains.order.OrderRepository;
import jpabook.jpashop.domains.order.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTestWithMock {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private ItemRepository itemRepository;


    @Test
    void 상품주문() throws Exception {
        //given
            // 멤버 저장
        Long TEST_MEMBER_ID = 1L;
        Member TEST_MEMBER = createMember("Test", TEST_MEMBER_ID);
        given(memberService.findOne(TEST_MEMBER_ID))
                .willReturn(TEST_MEMBER);

            // 상품 저장
        Long TEST_ITEM_ID = 2L;
        int stockQuantity = 5;
        Item TEST_ITEM = createItem("Test", stockQuantity, TEST_ITEM_ID);
        given(itemRepository.findById(any()))
                .willReturn(Optional.of(TEST_ITEM));

            // 주문 요청
        int orderCount = 2;
        int nowPrice = TEST_ITEM.getPrice();
        OrderRequest orderRequest = createOrderRequest(TEST_ITEM_ID, nowPrice, orderCount);

            // 주문
        Long TEST_ORDER_ID = 3L;
        Order TEST_ORDER = createOrder(TEST_MEMBER, TEST_ITEM, nowPrice, orderCount, TEST_ORDER_ID);
        given(orderRepository.save(any(Order.class)))
                .willReturn(TEST_ORDER);

        //when
        Long orderId = orderService.order(TEST_MEMBER_ID, orderRequest);

        //then
        then(orderRepository).should(atLeastOnce()).save(any(Order.class));
    }

    @Test
    void 주문취소() throws Exception {
        //given
        Long TEST_ORDER_ID = 1L;
        Order TEST_ORDER = mock(Order.class);
        willDoNothing()
                .given(TEST_ORDER).cancel();
        given(orderRepository.findById(any()))
                .willReturn(Optional.of(TEST_ORDER));

        //when
        orderService.cancelOrder(TEST_ORDER_ID);

        //then
        then(TEST_ORDER).should(atLeastOnce()).cancel();
    }

     @Test
     void 재고수량_초과_주문시_에러() throws Exception {
         //given
         Long TEST_MEMBER_ID = 1L;
         Member TEST_MEMBER = createMember("Test", TEST_MEMBER_ID);
         given(memberService.findOne(any()))
                 .willReturn(TEST_MEMBER);

         Long TEST_ITEM_ID = 2L;
         int stockQuantity = 1;
         Item TEST_ITEM = createItem("Test", stockQuantity, TEST_ITEM_ID);
         given(itemRepository.findById(any()))
                 .willReturn(Optional.of(TEST_ITEM));

         //when
         int orderCount = 2;

         //then
         assertThatThrownBy(() -> orderService.order(TEST_MEMBER.getId(), createOrderRequest(TEST_ITEM.getId(), TEST_ITEM.getPrice(), orderCount)))
                 .isInstanceOf(NotEnoughStockException.class).hasMessageContaining("need more stock");

    }

    private Order createOrder(Member TEST_MEMBER, Item TEST_ITEM, int price, int orderCount, Long TEST_ORDER_ID) {
        Order order = Order.builder()
                .member(TEST_MEMBER)
                .delivery(new Delivery(TEST_MEMBER.getAddress()))
                .orderItemList(List.of(
                        OrderItem.builder()
                                .item(TEST_ITEM)
                                .orderPrice(price)
                                .orderQuantity(orderCount)
                                .build()
                ))
                .build();
        ReflectionTestUtils.setField(order, "id", TEST_ORDER_ID);
        return order;
    }

    private OrderRequest createOrderRequest(Long itemId, int price, int orderCount) {
        List<OrderItemRequest> orderItemRequestList = List.of(new OrderItemRequest(itemId, price, orderCount));
        return new OrderRequest(orderItemRequestList);
    }

    private Item createItem(String str, int stockQuantity, Long itemId) {
        Item item = Item.builder()
                .itemName(str)
                .price(1000)
                .stockQuantity(stockQuantity)
                .imagePath(str)
                .categoryId(10L)
                .build();
        ReflectionTestUtils.setField(item, "id", itemId);
        return item;
    }

    private Member createMember(String str, Long memberId) {
        Member member = Member.builder()
                .authId(str)
                .authPassword(str)
                .address(new Address(str, str, str))
                .build();
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }
}
