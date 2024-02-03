package com.getcode.service.member;

import com.getcode.domain.member.Member;
import com.getcode.repository.MemberRepository;
import com.getcode.service.member.request.SignupDto;
import com.getcode.service.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse signup(SignupDto req) {
        Member member = req.toEntity();
        member.passwordEncoding(passwordEncoder);
        Member savedMember = memberRepository.save(member);
        return MemberResponse.of(savedMember);
    }

}

