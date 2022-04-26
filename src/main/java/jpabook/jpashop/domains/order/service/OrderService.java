package jpabook.jpashop.domains.order.service;

import jpabook.jpashop.domains.delivery.Delivery;
import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.MemberRepository;
import jpabook.jpashop.domains.member.service.MemberService;
import jpabook.jpashop.domains.order.Order;
import jpabook.jpashop.domains.order.OrderItem;
import jpabook.jpashop.domains.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberService memberService;


    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, OrderRequest orderRequest) {

        //엔티티 조회
        Member member = memberService.findOne(memberId);

        //배송지 생성
        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .build();

        //주문상품 생성
        List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    Item item = itemRepository.findById(orderItemRequest.getItemId()).get();
                    // 주문 상품 생성
                    return OrderItem.builder()
                            .item(item)
                            .orderPrice(orderItemRequest.getPrice())
                            .orderQuantity(orderItemRequest.getOrderCount())
                            .build();
                }).collect(Collectors.toList());

        //주문상품 재고 줄이기
        orderItemList.stream()
                .forEach(orderItem -> orderItem.removeStockQuantity());


        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .orderItemList(orderItemList)
                .build();
        order.changeDelivery(delivery);
        //Todo 이 밑에꺼는 뭔가 이상할수 있다.
//        orderItemList.stream()
//                .forEach(orderItem -> order.addOrderItem(orderItem));

        // 주문 저장
        return orderRepository.save(order).getId();

    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).get();
        //주문 취소
        order.cancel();
    }

    /** 주문 검색*/
    public List<Order> searchOrders(OrderSearch orderSearch) {
        return orderRepository.findByOrderSearch(orderSearch.getOrderStatus(), orderSearch.getMemberName());
    }
}
