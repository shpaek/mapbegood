package com.kosa.mapbegood.domain.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail,
                          String title,
                          String authCode,
                          String nickName) throws Exception {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, authCode, nickName);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, title: {}, text: {}", toEmail, title, authCode);
            throw new MailSendException("Error: MailService");
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String authCode,
                                              String nickName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText("" +
                "<div>" +
                    "<table>" +
                        "<h2>" + nickName + "님, 안녕하세요.</h2>" +
                        "사이트에서 인증번호를 입력해주세요." +
                        "감사합니다." +

                        "인증번호: <b>" + authCode + "</b>" +
                    "</table>" +
                "</div>" +
                "");

        return message;
    }
}
