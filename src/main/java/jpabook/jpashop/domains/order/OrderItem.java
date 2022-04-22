package jpabook.jpashop.domains.order;

import jpabook.jpashop.domains.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 당시 가격
    private int orderQuantity; //주문 수량

    @Builder
    public OrderItem(Item item, Order order, int orderPrice, int orderQuantity) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        changeOrder(order); // 양방향 연관관계 편의 메서드
    }

    //양방향 연관관계 편의메서드 - Order
    private void changeOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }
}
