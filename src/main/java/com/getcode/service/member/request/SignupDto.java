package com.getcode.service.member.request;

import static com.getcode.domain.member.Role.*;

import com.getcode.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    private SignupDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(ROLE_GUEST)
                .build();
    }
}
