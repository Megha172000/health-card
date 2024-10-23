package com.example.healthCard.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

  public static int generateRandomNumber() {
    return (int) (Math.random() * 900000) + 100000;
  }
}
