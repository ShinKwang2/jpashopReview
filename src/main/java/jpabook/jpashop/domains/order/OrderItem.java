package jpabook.jpashop.domains.order;

import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

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
    public OrderItem(Item item, int orderPrice, int orderQuantity) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }

    //양방향 연관관계 편의메서드 - Order
    public void changeOrder(Order order) {
        this.order = order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 아이템 취소 - 재고 수량 원복
     */
    public void cancel() {
        getItem().addStock(this.orderQuantity);
    }

    /**
     * 주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getOrderQuantity();
    }

    /**
     * 주문 아이템 생성 - 재고 수량 빼기
     */
    public void removeStockQuantity() {
        this.item.removeStock(orderQuantity);
    }
}
