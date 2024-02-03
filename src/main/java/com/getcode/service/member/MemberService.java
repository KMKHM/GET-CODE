package com.getcode.service.member;

import com.getcode.config.mail.MailService;
import com.getcode.config.redis.RedisService;
import com.getcode.domain.member.Member;
import com.getcode.repository.MemberRepository;
import com.getcode.service.member.request.SignupDto;
import com.getcode.service.member.response.MemberResponse;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final RedisService redisService;

    @Value("${mail.expiration}")
    private long authCodeExpirationMills;

    @Value("${mail.length}")
    private int length;

    @Value("${mail.chars}")
    private String characters;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Transactional
    public MemberResponse signup(SignupDto req) {
        Member member = req.toEntity();
        member.passwordEncoding(passwordEncoder);
        Member savedMember = memberRepository.save(member);
        return MemberResponse.of(savedMember);
    }

    public void sendCode(String email) {
        String title = "이메일 인증 번호";
        String authCode = createCode();

        mailService.sendEmail(email, title, authCode);

        redisService.setValues(AUTH_CODE_PREFIX + email, authCode, Duration.ofMillis(authCodeExpirationMills));
    }

    // 인증번호 생성로직
    private String createCode() {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}

