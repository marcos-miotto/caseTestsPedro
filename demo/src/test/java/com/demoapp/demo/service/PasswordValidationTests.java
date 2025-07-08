package com.demoapp.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Validação de Senha")
class PasswordValidationTests {

  private UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserService(null);
  }

  @Nested
  @DisplayName("Senhas Válidas")
  class ValidPasswords {

    @Test
    @DisplayName("Deve aceitar senha com todos os critérios atendidos")
    void shouldAcceptPasswordWithAllCriteria() {
      String password = "Password123!";
      assertTrue(userService.isPasswordValid(password), 
          "Senha deve ser válida quando atende todos os critérios");
    }

    @Test
    @DisplayName("Deve aceitar senha com caracteres especiais diferentes")
    void shouldAcceptPasswordWithDifferentSpecialChars() {
      String password = "Senha123@";
      assertTrue(userService.isPasswordValid(password), 
          "Senha deve ser válida com caractere especial @");
    }

    @Test
    @DisplayName("Deve aceitar senha com 8 caracteres exatos")
    void shouldAcceptPasswordWithExactly8Chars() {
      String password = "Pass1!@#";
      assertTrue(userService.isPasswordValid(password), 
          "Senha deve ser válida com exatamente 8 caracteres");
    }
  }

  @Nested
  @DisplayName("Senhas Inválidas")
  class InvalidPasswords {

    @Test
    @DisplayName("Deve rejeitar senha sem caracteres especiais")
    void shouldRejectPasswordWithoutSpecialChars() {
      String password = "Password123";
      assertFalse(userService.isPasswordValid(password), 
          "Senha deve ser inválida sem caracteres especiais");
    }

    @Test
    @DisplayName("Deve rejeitar senha muito curta")
    void shouldRejectPasswordTooShort() {
      String password = "Pass1!";
      assertFalse(userService.isPasswordValid(password), 
          "Senha deve ser inválida com menos de 8 caracteres");
    }

    @Test
    @DisplayName("Deve rejeitar senha sem números")
    void shouldRejectPasswordWithoutNumbers() {
      String password = "Password!";
      assertFalse(userService.isPasswordValid(password), 
          "Senha deve ser inválida sem números");
    }

    @Test
    @DisplayName("Deve rejeitar senha sem letras maiúsculas")
    void shouldRejectPasswordWithoutUppercase() {
      String password = "senha123!";
      assertFalse(userService.isPasswordValid(password), 
          "Senha deve ser inválida sem letras maiúsculas");
    }

    @Test
    @DisplayName("Deve rejeitar senha sem letras minúsculas")
    void shouldRejectPasswordWithoutLowercase() {
      String password = "SENHA123!";
      assertFalse(userService.isPasswordValid(password), 
          "Senha deve ser inválida sem letras minúsculas");
    }

    @Test
    @DisplayName("Deve rejeitar senha vazia")
    void shouldRejectEmptyPassword() {
      String password = "";
      assertFalse(userService.isPasswordValid(password), 
          "Senha vazia deve ser inválida");
    }

    @Test
    @DisplayName("Deve rejeitar senha null")
    void shouldRejectNullPassword() {
      assertFalse(userService.isPasswordValid(null), 
          "Senha null deve ser inválida");
    }
  }
} 