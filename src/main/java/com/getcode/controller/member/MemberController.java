package com.getcode.controller.member;

import com.getcode.controller.member.request.MemberCreateRequest;
import com.getcode.service.member.MemberService;
import com.getcode.service.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/sign-up")
    public MemberResponse signup(MemberCreateRequest req) {
        return memberService.signup(req.toServiceRequest());
    }
}
