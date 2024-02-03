package com.getcode.domain.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 암호화")
    void passwordEncodeTest() {
        // given
        Member member = createMember("1234");

        //when
        member.passwordEncoding(passwordEncoder);

        // then
        assertThat(member.getPassword()).isNotEqualTo("1234");
    }

    private Member createMember(String password) {
        return Member.builder()
                .email("kyun9151@gmail.com")
                .password(password)
                .nickname("HoduMaru")
                .build();
    }
}
