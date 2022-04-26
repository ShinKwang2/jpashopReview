package jpabook.jpashop.domains.order;

import jpabook.jpashop.domains.order.service.OrderSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("select o from Order o join o.member m where o.status = :status and m.name like :name")
//    List<Order> findByOrderSearch(@Param("status") OrderStatus status, @Param("name") String memberName);
}
