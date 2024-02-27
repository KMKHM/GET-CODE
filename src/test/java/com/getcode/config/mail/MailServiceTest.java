package com.getcode.config.mail;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.aspectj.lang.annotation.Around;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class MailServiceTest {
    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void sendEmailTest() {
        //given
        MailService mailService = Mockito.mock(MailService.class);

        //when
        mailService.sendEmail("kyun9151@naver.com", "aa", "bb");

        //then
        Mockito.verify(mailService).sendEmail("kyun9151@naver.com", "aa", "bb");
    }
    
    @Test
    void createEmailFormTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kyun9151@naver.com");
        message.setSubject("제목");
        message.setText("내용");

        Assertions.assertThat(message.getText()).isEqualTo("내용");
        Assertions.assertThat(message.getTo()[0]).isEqualTo("kyun9151@naver.com");
        Assertions.assertThat(message.getSubject()).isEqualTo("제목");
    }

}