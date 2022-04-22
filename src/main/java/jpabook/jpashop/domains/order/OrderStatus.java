package jpabook.jpashop.domains.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDERED_STATUS("주문완료"), SHIPPING_STATUS("배송 중"),  CANCELED_STATUS("주문취소");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
