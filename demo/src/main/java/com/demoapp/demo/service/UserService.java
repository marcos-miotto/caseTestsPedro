package com.demoapp.demo.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.demoapp.demo.model.User;
import com.demoapp.demo.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean isEmailValid(String email) {
    return email != null && email.contains("@");
  }

  public boolean isPasswordValid(String password) {
    if (password == null) {
      return false;
    }
    String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
    return Pattern.matches(passRegex, password);
  }

  public User createUser(String email, String password) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    return userRepository.save(user);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

}
