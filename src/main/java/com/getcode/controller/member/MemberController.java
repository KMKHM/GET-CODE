package com.getcode.controller.member;

import com.getcode.controller.member.request.MemberCreateRequest;
import com.getcode.service.member.MemberService;
import com.getcode.service.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(req.toServiceRequest()));
    }
}
