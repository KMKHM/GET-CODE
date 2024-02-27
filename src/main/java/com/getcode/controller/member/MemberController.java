package com.getcode.controller.member;

import com.getcode.common.ApiResponse;
import com.getcode.controller.member.request.MemberCreateRequest;
import com.getcode.service.member.MemberService;
import com.getcode.service.member.response.EmailVerificationResponse;
import com.getcode.service.member.response.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody MemberCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(req.toServiceRequest()));
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendEmail(@RequestParam("email") String email) {
        memberService.sendCode(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/emails/verify-code")
    public ApiResponse<EmailVerificationResponse> verifyCode(@RequestParam("email") String email,
                                                             @RequestParam("code") String code) {
        return memberService.verifyCode(email, code);
    }
}
