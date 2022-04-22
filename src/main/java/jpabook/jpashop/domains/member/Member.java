package jpabook.jpashop.domains.member;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Builder
    private Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
