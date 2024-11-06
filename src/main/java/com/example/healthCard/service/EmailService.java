package com.example.healthCard.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
  @Autowired private JavaMailSender emailSender;

  @Autowired TemplateEngine templateEngine;

  public void sendEmail(String toEmail, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("your-sender-email@gmail.com");
    message.setTo(toEmail);
    message.setSubject(subject);
    message.setText(body);

    emailSender.send(message);

    System.out.println("Message sent successfully");
  }

  public void sendTempEmail(String name, String toEmail, String subject, int code) {
    try {
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      mimeMessageHelper.setTo(toEmail);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setFrom("meghapatidar172000@gmail.com");
      Context context = new Context();
      context.setVariable("name", name);
      context.setVariable(
          "url", "http://prjhealthcard.com:8080/activate-agent?email=" + toEmail + "&code=" + code);
      String htmlContent = templateEngine.process("email", context);
      mimeMessageHelper.setText(htmlContent, true);
      emailSender.send(mimeMessage);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
