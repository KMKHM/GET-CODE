package com.getcode.service.member;

import com.getcode.controller.member.request.MemberCreateRequest;
import com.getcode.domain.member.Member;
import com.getcode.repository.MemberRepository;
import com.getcode.service.member.request.SignupDto;
import com.getcode.service.member.response.MemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse signup(SignupDto req) {
        Member savedMember = memberRepository.save(req.toEntity());
        return MemberResponse.of(savedMember);
    }

}

