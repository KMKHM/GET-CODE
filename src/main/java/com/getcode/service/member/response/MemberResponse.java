package com.getcode.service.member.response;

import com.getcode.domain.member.Member;
import lombok.Builder;

public class MemberResponse {
    private String email;
    private String password;
    private String nickname;

    @Builder
    private MemberResponse(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .build();
    }
}
