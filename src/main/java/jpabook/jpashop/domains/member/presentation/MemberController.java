package jpabook.jpashop.domains.member.presentation;

import jpabook.jpashop.domains.member.service.MemberService;
import jpabook.jpashop.domains.member.service.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller("/auth")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "members/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute @Valid SignUpRequest request) {
        memberService.signUp(request);
        return "redirect:/auth/signIn";
    }

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "members/signIn";
    }
}
