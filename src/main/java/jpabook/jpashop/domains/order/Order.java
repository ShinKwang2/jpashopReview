package jpabook.jpashop.domains.order;

import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.delivery.Delivery;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;



    @Builder
    public Order(Member member, List<OrderItem> orderItems, Delivery delivery) {
        this.member = member;
        this.orderItems = orderItems;
        changeDelivery(delivery); // 양방향 편의 메서드
        this.status = OrderStatus.ORDERED_STATUS;
    }

    // 양방향 연관관계 편의 메서드
    private void changeDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.changeOrder(this);
    }
}
