package jpabook.jpashop.domains.member.service;

import jpabook.jpashop.domains.Address;
import jpabook.jpashop.domains.member.Member;
import jpabook.jpashop.domains.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long signUp(SignUpRequest request) {
        validateDuplicateMember(request.getAuthId());

        Member newMember = request.toEntity();
        Long memberId = memberRepository.save(newMember).getId();

        return memberId;
    }

    //DB의 authId를 유니크 제약조건으로 걸어주는 것이 좋다. 나중에 배워서 할 것!
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
