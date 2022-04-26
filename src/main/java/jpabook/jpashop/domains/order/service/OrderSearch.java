package jpabook.jpashop.domains.order.service;

import jpabook.jpashop.domains.order.OrderStatus;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderSearch {

    private String memberName;  // 회원 이름
    private OrderStatus orderStatus;    // 주문 상태 (ORDER, SHIPPING, CANCELED)
}
