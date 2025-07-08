package com.demoapp.demo.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demoapp.demo.dto.UserDTO;
import com.demoapp.demo.model.User;
import com.demoapp.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@DisplayName("Testes de Integração - AuthController")
class AuthControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private UserDTO validUserDTO;
  private User validUser;

  @BeforeEach
  void setUp() {
    validUserDTO = new UserDTO();
    validUserDTO.setEmail("teste@exemplo.com");
    validUserDTO.setPassword("Senha123!");

    validUser = new User();
    validUser.setId(1L);
    validUser.setEmail("teste@exemplo.com");
    validUser.setPassword("Senha123!");
  }

  @Nested
  @DisplayName("Cadastro de Usuário")
  class UserSignup {

    @Test
    @DisplayName("Deve cadastrar usuário com dados válidos")
    void shouldSignupUserWithValidData() throws Exception {
      // Arrange
      when(userService.isEmailValid(anyString())).thenReturn(true);
      when(userService.isPasswordValid(anyString())).thenReturn(true);
      when(userService.findByEmail(anyString())).thenReturn(null);
      when(userService.createUser(anyString(), anyString())).thenReturn(validUser);

      // Act & Assert
      mockMvc.perform(post("/auth/signup")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(validUserDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1))
          .andExpect(jsonPath("$.email").value("teste@exemplo.com"));
    }

    @Test
    @DisplayName("Deve rejeitar cadastro com email inválido")
    void shouldRejectSignupWithInvalidEmail() throws Exception {
      // Arrange
      UserDTO invalidEmailDTO = new UserDTO();
      invalidEmailDTO.setEmail("email-invalido");
      invalidEmailDTO.setPassword("Senha123!");

      when(userService.isEmailValid("email-invalido")).thenReturn(false);

      // Act & Assert
      mockMvc.perform(post("/auth/signup")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(invalidEmailDTO)))
          .andExpect(status().isUnprocessableEntity())
          .andExpect(jsonPath("$.message").value("E-mail inválido"))
          .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    @DisplayName("Deve rejeitar cadastro com senha inválida")
    void shouldRejectSignupWithInvalidPassword() throws Exception {
      // Arrange
      UserDTO invalidPasswordDTO = new UserDTO();
      invalidPasswordDTO.setEmail("teste@exemplo.com");
      invalidPasswordDTO.setPassword("123456");

      when(userService.isEmailValid(anyString())).thenReturn(true);
      when(userService.isPasswordValid("123456")).thenReturn(false);

      // Act & Assert
      mockMvc.perform(post("/auth/signup")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(invalidPasswordDTO)))
          .andExpect(status().isUnprocessableEntity())
          .andExpect(jsonPath("$.message").value("Senha inválida"))
          .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    @DisplayName("Deve rejeitar cadastro com email já existente")
    void shouldRejectSignupWithExistingEmail() throws Exception {
      // Arrange
      when(userService.isEmailValid(anyString())).thenReturn(true);
      when(userService.isPasswordValid(anyString())).thenReturn(true);
      when(userService.findByEmail(anyString())).thenReturn(validUser);

      // Act & Assert
      mockMvc.perform(post("/auth/signup")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(validUserDTO)))
          .andExpect(status().isConflict())
          .andExpect(jsonPath("$.message").value("E-mail já está em uso"))
          .andExpect(jsonPath("$.status").value(409));
    }
  }

  @Nested
  @DisplayName("Login de Usuário")
  class UserSignin {

    @Test
    @DisplayName("Deve fazer login com credenciais válidas")
    void shouldSigninWithValidCredentials() throws Exception {
      // Arrange
      when(userService.isEmailValid(anyString())).thenReturn(true);
      when(userService.isPasswordValid(anyString())).thenReturn(true);
      when(userService.findByEmail(anyString())).thenReturn(validUser);

      // Act & Assert
      mockMvc.perform(post("/auth/signin")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(validUserDTO)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.token").value("fake_token"));
    }

    @Test
    @DisplayName("Deve rejeitar login com credenciais inválidas")
    void shouldRejectSigninWithInvalidCredentials() throws Exception {
      // Arrange
      when(userService.isEmailValid(anyString())).thenReturn(true);
      when(userService.isPasswordValid(anyString())).thenReturn(true);
      when(userService.findByEmail(anyString())).thenReturn(null);

      // Act & Assert
      mockMvc.perform(post("/auth/signin")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(validUserDTO)))
          .andExpect(status().isUnauthorized())
          .andExpect(jsonPath("$.message").value("Credenciais inválidas"))
          .andExpect(jsonPath("$.status").value(401));
    }
  }
} 