package jpabook.jpashop.domains.member.service;

import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .authId("sklee0206")
                .password("1234")
                .name("LEE")
                .phone("01099999999")
                .city("city")
                .street("steet")
                .zipcode("zipcode")
                .build();

        Member member = request.toEntity();

        //when
        Long savedMemberId = memberService.signUp(request);

        //then
        assertThat(member.getAuthId()).isEqualTo(memberService.findOne(savedMemberId).getAuthId());
        assertThat(member.getAuthPassword()).isEqualTo(memberService.findOne(savedMemberId).getAuthPassword());
        assertThat(member.getName()).isEqualTo(memberService.findOne(savedMemberId).getName());
        assertThat(member.getPhone()).isEqualTo(memberService.findOne(savedMemberId).getPhone());
        assertThat(member.getAddress().getCity()).isEqualTo(memberService.findOne(savedMemberId).getAddress().getCity());
     }

     @Test
     void 중복_회원_예외() throws Exception {
         //given
         SignUpRequest request = SignUpRequest.builder()
                 .authId("sklee0206")
                 .password("1234")
                 .name("LEE")
                 .phone("01099999999")
                 .city("city")
                 .street("steet")
                 .zipcode("zipcode")
                 .build();

         memberService.signUp(request);

         //when
         SignUpRequest duplicateRequest = SignUpRequest.builder()
                 .authId("sklee0206")
                 .password("1234")
                 .name("LEE")
                 .phone("01099999999")
                 .city("city")
                 .street("steet")
                 .zipcode("zipcode")
                 .build();


         //then
         assertThatIllegalStateException().isThrownBy(() -> memberService.signUp(duplicateRequest));


      }
}