package com.example.healthCard.controller;

import com.example.healthCard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {

  @Autowired EmailService emailService;

  @GetMapping("/send-email")
  @ResponseBody
  public String sendEmail() {
    emailService.sendEmail("meghapatidar172000@gmail.com", "Test Email", "John Doe");
    return "email";
  }
}
