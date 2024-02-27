package com.getcode.service.member;

import static com.getcode.domain.member.Role.*;

import com.getcode.common.ApiResponse;
import com.getcode.config.mail.MailService;
import com.getcode.config.redis.RedisService;
import com.getcode.domain.member.Member;
import com.getcode.domain.member.Role;
import com.getcode.repository.MemberRepository;
import com.getcode.service.member.request.SignupDto;
import com.getcode.service.member.response.EmailVerificationResponse;
import com.getcode.service.member.response.MemberResponse;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    // 인증번호 검증
    @Transactional
    public ApiResponse<EmailVerificationResponse> verifyCode(String email, String code) {
        if (checkCode(email, code)) {
            Member findMember = memberRepository.findByEmail(email);
            findMember.updateRole(ROLE_USER);
            return ApiResponse.of(HttpStatus.OK, "이메일 인증 성공", EmailVerificationResponse.of(true));
        }
        return ApiResponse.of(HttpStatus.FORBIDDEN, "이메일 인증 실패", EmailVerificationResponse.of(false));
    }

    private boolean checkCode(String email, String code) {
        return code.equals(redisService.getValues(AUTH_CODE_PREFIX + email));
    }
}

