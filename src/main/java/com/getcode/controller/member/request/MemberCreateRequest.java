package com.getcode.controller.member.request;

import com.getcode.service.member.request.SignupDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {
    private String email;
    private String password;
    private String nickname;

    @Builder
    private MemberCreateRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public SignupDto toServiceRequest() {
        return SignupDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
