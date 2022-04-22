package jpabook.jpashop.domains.delivery;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.BaseEntity;
import jpabook.jpashop.domains.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP

    @Builder
    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY_STATUS;
    }


    /**
     * 양방향 편의메소드를 위한 추가 메서드, 그런데 public 으로 밖에 할 수 없나..?
     * 그렇다면 매개변수에 final 을 넣을까?
     * 근데 나중에 Order 와 Delivery 를 따로 끌고가게 된다면? 일단 넣지 말자.
     */
    public void changeOrder(Order order) {
        this.order = order;
    }

}
