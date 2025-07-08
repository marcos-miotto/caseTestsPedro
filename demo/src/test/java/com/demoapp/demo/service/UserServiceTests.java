package com.demoapp.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

  @Test
  @DisplayName("Test if password is valid according to regex")
  void testIsPasswordValid() {
    String password = "Password123!";
    UserService userService = new UserService(null);
    boolean isValid = userService.isPasswordValid(password);
    assertTrue(isValid, "Password should be valid according to the regex.");
  }

  @Test
  @DisplayName("Test if password is invalid according to regex")
  void testIsPasswordInvalid() {
    String password = "password123";
    UserService userService = new UserService(null);
    boolean isValid = userService.isPasswordValid(password);
    assertTrue(!isValid, "Password should be invalid according to the regex.");
  }
}
