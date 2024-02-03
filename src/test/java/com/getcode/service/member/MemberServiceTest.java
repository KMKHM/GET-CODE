package com.getcode.service.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.getcode.controller.member.request.MemberCreateRequest;
import com.getcode.domain.member.Member;
import com.getcode.domain.member.Role;
import com.getcode.repository.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("회원가입")
    @Test
    public void signup() {
        //given
        Member member = createMember("kyun9151@naver.com", "hodu");

        MemberCreateRequest request = MemberCreateRequest.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .build();

        //when
        memberService.signup(request.toServiceRequest());

        //then
        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(1)
                .extracting("email", "nickname")
                .containsExactly(tuple("kyun9151@naver.com", "hodu"));

        assertThat(members.get(0).getPassword()).isNotEqualTo("1234");
        assertThat(members.get(0).getRole()).isEqualTo(Role.ROLE_GUEST);
    }

    private Member createMember(String email, String nickname) {
        return Member.builder()
                .email(email)
                .password("1234")
                .nickname(nickname)
                .build();
    }
}