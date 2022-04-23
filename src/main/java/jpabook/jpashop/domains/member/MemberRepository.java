package jpabook.jpashop.domains.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAuthId(String authId);

    List<Member> findByName(String name);

}
