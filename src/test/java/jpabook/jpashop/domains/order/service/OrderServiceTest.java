package jpabook.jpashop.domains.order.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.category.Category;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import jpabook.jpashop.domains.item.service.NotEnoughStockException;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.service.MemberService;
import jpabook.jpashop.domains.member.service.SignUpRequest;
import jpabook.jpashop.domains.order.Order;
import jpabook.jpashop.domains.order.OrderItem;
import jpabook.jpashop.domains.order.OrderRepository;
import jpabook.jpashop.domains.order.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        //given
        Member member = createMember("Test");
        em.persist(member);

        Item item = createItem("Test");
        em.persist(item);


        //when
        int orderCount = 10;
        OrderRequest orderRequest = createOrderRequest(item.getId(), item.getPrice(), orderCount);
        Long orderId = orderService.order(member.getId(), orderRequest);
        Order getOrder = orderRepository.findById(orderId).get();

        //then
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDERED_STATUS);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(1000 * orderCount);
        assertThat(item.getStockQuantity()).isEqualTo(90);
    }
    
    @Test
    void 재고수량_초과() throws Exception {
        //given
        Member member = createMember("Test");
        em.persist(member);

        Item item = createItem("Test");
        em.persist(item);

        int orderCount = 101;
        OrderRequest orderRequest = createOrderRequest(item.getId(), item.getPrice(), orderCount);


        //when, then
        assertThatThrownBy(() -> orderService.order(member.getId(), orderRequest))
                .isInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("need more stock");
    }
    
    @Test
    void 주문취소() throws Exception {
        //given
        Member member = createMember("Test");
        em.persist(member);

        Item item = createItem("Test");
        em.persist(item);

        int orderCount = 50;
        OrderRequest orderRequest = createOrderRequest(item.getId(), item.getPrice(), orderCount);

        Long orderId = orderService.order(member.getId(), orderRequest);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId).get();

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCELED_STATUS);
        assertThat(item.getStockQuantity()).isEqualTo(100);
    }
    
    
    private OrderRequest createOrderRequest(Long itemId, int price, int orderCount) {
        List<OrderItemRequest> orderItemRequestList = List.of(new OrderItemRequest(itemId, price, orderCount));
        return new OrderRequest(orderItemRequestList);
    }

    private Item createItem(String str) {
        return Item.builder()
                .imagePath(str)
                .itemName(str)
                .price(1000)
                .stockQuantity(100)
                .categoryId(10L)
                .build();
    }

    private Member createMember(String str) {
        return Member.builder()
                .authId(str)
                .authPassword(str)
                .name(str)
                .phone(str)
                .address(new Address(str, str, str))
                .build();
    }
}