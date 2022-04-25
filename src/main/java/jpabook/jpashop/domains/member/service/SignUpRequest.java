package jpabook.jpashop.domains.member.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.member.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SignUpRequest {

    @NotBlank
    private String authId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @Builder
    public SignUpRequest(String authId, String password,
                         String name, String phone,
                         String city, String street, String zipcode) {
        this.authId = authId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Member toEntity() {
        return Member.builder()
                .authId(authId)
                .authPassword(password)
                .name(name)
                .phone(phone)
                .address(new Address(city, street, zipcode))
                .build();

    }
}
