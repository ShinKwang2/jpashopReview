package jpabook.jpashop.domains.member.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTestWithMock {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원가입() throws Exception {
        //given
        SignUpRequest request = createSignUpRequest("Test");
        Long fakeMemberId = 1L;

        Member member = createMember(request, fakeMemberId);
        given(memberRepository.save(any(Member.class)))
                .willReturn(member);
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(member));

        //when
        Long savedMemberId = memberService.signUp(request);

        //then
        Member findMember = memberRepository.findById(savedMemberId).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        String duplicatedAuthId = "Test";
        SignUpRequest request = createSignUpRequest(duplicatedAuthId);

        Member member = createMember(request, 1L);
        given(memberRepository.findByAuthId(duplicatedAuthId))
                .willReturn(Optional.of(member));

        //when
        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 회원입니다.");
        //then
    }

    @Test
    void 없는_회원_검색() throws Exception {
        //given
        Long NO_MEMBER_ID = 100L;

        //when, then
        assertThatThrownBy(() -> memberService.findOne(NO_MEMBER_ID)).isInstanceOf(IllegalStateException.class).hasMessageContaining("존재하지 않는 유저입니다.");

     }

    private Member createMember(SignUpRequest request, Long fakeMemberId) {
        Member newMember = Member.builder()
                .authId(request.getAuthId())
                .authPassword(request.getPassword())
                .name(request.getName())
                .phone(request.getPhone())
                .address(new Address(request.getCity(), request.getStreet(), request.getZipcode()))
                .build();
        ReflectionTestUtils.setField(newMember, "id", fakeMemberId);
        return newMember;
    }

    private SignUpRequest createSignUpRequest(String str) {
        return SignUpRequest.builder()
                .authId(str)
                .password(str)
                .name(str)
                .phone(str)
                .city(str)
                .street(str)
                .zipcode(str)
                .build();
    }
}
