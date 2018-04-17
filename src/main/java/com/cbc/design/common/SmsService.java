package com.cbc.design.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * Created by cbc on 2018/3/29.
 */
@EnableAsync
@Component
@Slf4j
public class SmsService {

    private final JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    public SmsService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("邮件已经发送。");
        }catch (Exception e){
            log.error("发送邮件时发生异常！");
        }
    }



}
