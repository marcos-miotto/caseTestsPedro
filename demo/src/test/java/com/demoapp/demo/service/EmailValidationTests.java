package com.demoapp.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Validação de Email")
class EmailValidationTests {

  private UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserService(null);
  }

  @Nested
  @DisplayName("Emails Válidos")
  class ValidEmails {

    @Test
    @DisplayName("Deve aceitar email com formato padrão")
    void shouldAcceptStandardEmail() {
      String email = "usuario@exemplo.com";
      assertTrue(userService.isEmailValid(email), 
          "Email com formato padrão deve ser válido");
    }

    @Test
    @DisplayName("Deve aceitar email com subdomínio")
    void shouldAcceptEmailWithSubdomain() {
      String email = "usuario@sub.exemplo.com";
      assertTrue(userService.isEmailValid(email), 
          "Email com subdomínio deve ser válido");
    }

    @Test
    @DisplayName("Deve aceitar email com números")
    void shouldAcceptEmailWithNumbers() {
      String email = "user123@exemplo.com";
      assertTrue(userService.isEmailValid(email), 
          "Email com números deve ser válido");
    }

    @Test
    @DisplayName("Deve aceitar email com underscore")
    void shouldAcceptEmailWithUnderscore() {
      String email = "user_name@exemplo.com";
      assertTrue(userService.isEmailValid(email), 
          "Email com underscore deve ser válido");
    }

    @Test
    @DisplayName("Deve aceitar email com ponto no nome")
    void shouldAcceptEmailWithDotInName() {
      String email = "user.name@exemplo.com";
      assertTrue(userService.isEmailValid(email), 
          "Email com ponto no nome deve ser válido");
    }
  }

  @Nested
  @DisplayName("Emails Inválidos")
  class InvalidEmails {

    @Test
    @DisplayName("Deve rejeitar email sem @")
    void shouldRejectEmailWithoutAt() {
      String email = "usuarioexemplo.com";
      assertFalse(userService.isEmailValid(email), 
          "Email sem @ deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email sem domínio")
    void shouldRejectEmailWithoutDomain() {
      String email = "usuario@";
      assertFalse(userService.isEmailValid(email), 
          "Email sem domínio deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email sem nome de usuário")
    void shouldRejectEmailWithoutUsername() {
      String email = "@exemplo.com";
      assertFalse(userService.isEmailValid(email), 
          "Email sem nome de usuário deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email vazio")
    void shouldRejectEmptyEmail() {
      String email = "";
      assertFalse(userService.isEmailValid(email), 
          "Email vazio deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email null")
    void shouldRejectNullEmail() {
      assertFalse(userService.isEmailValid(null), 
          "Email null deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email com espaços")
    void shouldRejectEmailWithSpaces() {
      String email = "usuario @exemplo.com";
      assertFalse(userService.isEmailValid(email), 
          "Email com espaços deve ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar email com múltiplos @")
    void shouldRejectEmailWithMultipleAt() {
      String email = "usuario@@exemplo.com";
      assertFalse(userService.isEmailValid(email), 
          "Email com múltiplos @ deve ser inválido");
    }
  }
} 