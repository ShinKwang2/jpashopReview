package jpabook.jpashop.domains.member;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String authId;
    private String authPassword;

    private String name;
    private String phone;

    @Embedded
    private Address address;

    @Builder
    public Member(String authId, String authPassword, String name, String phone, Address address) {
        this.authId = authId;
        this.authPassword = authPassword;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
