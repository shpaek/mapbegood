package com.kosa.mapbegood.domain.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
        try {
            MimeMessage mmsg = this.createEmailForm(toEmail, title, authCode, nickName);
            emailSender.send(mmsg);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, title: {}, text: {}", toEmail, title, authCode);
            throw new MailSendException("Error: MailService");
        }
    }

    private MimeMessage createEmailForm(String toEmail,
                                        String title,
                                        String authCode,
                                        String nickName) throws MessagingException {

        MimeMessage mmsg = emailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mmsg, true);

        mmh.setTo(toEmail);
        mmh.setSubject(title);

        String htmlmsg =
                "<html>\n" +
                        "  <body>\n" +
                        "    <div class=\"auth-layout\">\n" +
                        "      <div\n" +
                        "        style=\"\n" +
                        "          margin: auto;\n" +
                        "          width: 500px;\n" +
                        "          height: 500px;\n" +
                        "          text-align: center;\n" +
                        "          font-weight: bolder;\n" +
                        "        \"\n" +
                        "      >\n" +
                        "        <div class=\"auth-title\" style=\"margin-top: 50px; text-align: left\">\n" +
                        "          <a href=\"http://localhost:5173\">\n" +
                        "            <img\n" +
                        "              src=\"https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/image/mainlogo.png\"\n" +
                        "              alt=\"mapbegood\"\n" +
                        "              style=\"width: 25px\"\n" +
                        "            />\n" +
                        "            <h2\n" +
                        "              style=\"\n" +
                        "                display: inline-block;\n" +
                        "                margin: auto;\n" +
                        "                margin-bottom: 10px;\n" +
                        "                text-decoration-line: none;\n" +
                        "                text-decoration: none;\n" +
                        "                color: #000000;\n" +
                        "              \"\n" +
                        "            >\n" +
                        "              좋을지도\n" +
                        "            </h2>\n" +
                        "          </a>\n" +
                        "        </div>\n" +
                        "        <div\n" +
                        "          class=\"auth-container\"\n" +
                        "          style=\"\n" +
                        "            border: 1px solid gray;\n" +
                        "            border-radius: 20px;\n" +
                        "            background-color: rgb(203, 226, 252);\n" +
                        "            margin: auto;\n" +
                        "            width: 500px;\n" +
                        "            height: 380px;\n" +
                        "            text-align: center;\n" +
                        "            font-weight: bolder;\n" +
                        "            color: #000000\n" +
                        "          \"\n" +
                        "        >\n" +
                        "          <br />" + nickName + " 님, 안녕하세요.<br /><br />\n" +
                        "          이메일 인증을 완료하시려면<br />\n" +
                        "          <b style=\"color: blue\">아래의 인증번호를 입력</b>\n" +
                        "          해주세요.<br /><br />\n" +
                        "\n" +
                        "          <div\n" +
                        "            class=\"auth-code-container\"\n" +
                        "            style=\"\n" +
                        "              border: 1px solid gray;\n" +
                        "              border-radius: 20px;\n" +
                        "              background-color: white;\n" +
                        "              margin-top: 20px;\n" +
                        "              margin-left: 10px;\n" +
                        "              margin-right: 10px;\n" +
                        "              height: 200px;\n" +
                        "            \"\n" +
                        "          >\n" +
                        "            <div class=\"auth-code-title\" style=\"margin-top: 10px\">인증번호</div>\n" +
                        "            <div class=\"line-container\">\n" +
                        "              <hr id=\"line\" />\n" +
                        "            </div>\n" +
                        "            <div\n" +
                        "              class=\"auth-code\"\n" +
                        "              style=\"margin-top: 40px; margin-bottom: 10px\"\n" +
                        "            >\n" +
                        "              <b style=\"font-size: larger\">" + authCode + "</b><br /><br />\n" +
                        "              <b style=\"font-size: smaller; color: rgb(122, 122, 122)\"\n" +
                        "                >※인증번호는 5분간 유효합니다.</b\n" +
                        "              >\n" +
                        "            </div>\n" +
                        "          </div>\n" +
                        "        </div>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>\n";

        mmh.setText(htmlmsg, true);

        return mmsg;
    }
}
