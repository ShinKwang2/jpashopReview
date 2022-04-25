package jpabook.jpashop.domains.order;

import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.delivery.DeliveryStatus;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.delivery.Delivery;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * todo 나중에 단방향으로 리팩터링 하기
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder
    public Order(Member member, Delivery delivery, List<OrderItem> orderItemList) {
        this.member = member;
        this.delivery = delivery;
        this.orderItems = orderItemList;
        this.status = OrderStatus.ORDERED_STATUS;
    }

    //== 양방향 연관관계 편의 메서드 ==//
    public void addOrderItem(OrderItem... orderItemList) {
        Arrays.stream(orderItemList)
                .forEach(orderItem -> this.orderItems.add(orderItem));

        Arrays.stream(orderItemList)
                .forEach(orderItem -> orderItem.changeOrder(this));
    }
    public void changeDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.changeOrder(this);
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMPLETE_STATUS
                || this.delivery.getStatus() == DeliveryStatus.SHIPPING_STATUS)
            throw new IllegalStateException("이미 배송중이거나 배송이 완료된 주문은 취소가 불가능합니다.");

        this.status = OrderStatus.CANCELED_STATUS;

        this.orderItems.stream()
                .forEach(orderItem -> orderItem.cancel());
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return this.orderItems.stream()
                .mapToInt(orderItems -> orderItems.getTotalPrice())
                .sum();
    }
}
