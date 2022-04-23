package jpabook.jpashop.domains.member.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long signUp(SignUpRequest request) {
        validateDuplicateMember(request.getAuthId());

        //회원 저장
        Member newMember = Member.builder()
                .authId(request.getAuthId())
                .authPassword(request.getPassword())
                .name(request.getName())
                .phone(request.getPhone())
                .address(new Address(request.getCity(), request.getStreet(), request.getZipcode()))
                .build();

        Long memberId = memberRepository.save(newMember).getId();

        return memberId;
    }

    private void validateDuplicateMember(String authId) {
        Optional<Member> findMember = memberRepository.findByAuthId(authId);

        if (findMember.isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }


    /**
     * 회원 전체 조회
     */
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        Member member = validateExistMember(memberRepository.findById(memberId));

        return member;
    }

    private Member validateExistMember(Optional<Member> member) {
        if (member.isEmpty())
            throw new IllegalStateException("존재하지 않는 유저입니다.");

        return member.get();
    }
}
