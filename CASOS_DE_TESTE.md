# Casos de Teste - SQA-25 Auth Tests

## Visão Geral
Este documento descreve 3 casos de teste críticos para o sistema de autenticação, cobrindo tanto o backend (Spring Boot) quanto o frontend (Next.js).

---

## Caso de Teste 1: Validação de Senha Forte

### **Objetivo**
Verificar se o sistema valida corretamente senhas que atendem aos critérios de segurança definidos.

### **Critérios de Aceitação**
- Senha deve conter pelo menos 8 caracteres
- Senha deve conter pelo menos uma letra minúscula
- Senha deve conter pelo menos uma letra maiúscula
- Senha deve conter pelo menos um número
- Senha deve conter pelo menos um caractere especial (@$!%*?&)

### **Cenários de Teste**

#### 1.1 Senha Válida
- **Dados de Entrada**: `"Password123!"`
- **Resultado Esperado**: `true`
- **Descrição**: Senha que atende a todos os critérios de segurança

#### 1.2 Senha Inválida - Sem Caracteres Especiais
- **Dados de Entrada**: `"Password123"`
- **Resultado Esperado**: `false`
- **Descrição**: Senha sem caracteres especiais

#### 1.3 Senha Inválida - Muito Curta
- **Dados de Entrada**: `"Pass1!"`
- **Resultado Esperado**: `false`
- **Descrição**: Senha com menos de 8 caracteres

#### 1.4 Senha Inválida - Sem Números
- **Dados de Entrada**: `"Password!"`
- **Resultado Esperado**: `false`
- **Descrição**: Senha sem números

### **Implementação**
- **Backend**: Teste unitário no `UserService.isPasswordValid()`
- **Frontend**: Teste unitário na função `validatePassword()`

---

## Caso de Teste 2: Fluxo de Cadastro de Usuário

### **Objetivo**
Verificar se o sistema processa corretamente o cadastro de novos usuários, incluindo validações e tratamento de erros.

### **Critérios de Aceitação**
- Sistema deve validar email antes do cadastro
- Sistema deve validar senha antes do cadastro
- Sistema deve impedir cadastro com email já existente
- Sistema deve criar usuário com dados válidos
- Sistema deve retornar respostas HTTP apropriadas

### **Cenários de Teste**

#### 2.1 Cadastro com Dados Válidos
- **Dados de Entrada**: 
  - Email: `"teste@exemplo.com"`
  - Senha: `"Senha123!"`
- **Resultado Esperado**: 
  - Status: `200 OK`
  - Usuário criado no banco de dados
- **Descrição**: Cadastro bem-sucedido com dados válidos

#### 2.2 Cadastro com Email Inválido
- **Dados de Entrada**: 
  - Email: `"email-invalido"`
  - Senha: `"Senha123!"`
- **Resultado Esperado**: 
  - Status: `422 Unprocessable Entity`
  - Mensagem: `"E-mail inválido"`
- **Descrição**: Tentativa de cadastro com email mal formatado

#### 2.3 Cadastro com Email Já Existente
- **Dados de Entrada**: 
  - Email: `"usuario@exemplo.com"` (já cadastrado)
  - Senha: `"Senha123!"`
- **Resultado Esperado**: 
  - Status: `409 Conflict`
  - Mensagem: `"E-mail já está em uso"`
- **Descrição**: Tentativa de cadastro com email já registrado

#### 2.4 Cadastro com Senha Fraca
- **Dados de Entrada**: 
  - Email: `"novo@exemplo.com"`
  - Senha: `"123456"`
- **Resultado Esperado**: 
  - Status: `422 Unprocessable Entity`
  - Mensagem: `"Senha inválida"`
- **Descrição**: Tentativa de cadastro com senha que não atende aos critérios

### **Implementação**
- **Backend**: Teste de integração no `AuthController.signup()`
- **Frontend**: Teste de integração no serviço `authService.signUp()`

---

## Caso de Teste 3: Validação de Email

### **Objetivo**
Verificar se o sistema valida corretamente o formato de email em diferentes contextos.

### **Critérios de Aceitação**
- Sistema deve aceitar emails com formato válido
- Sistema deve rejeitar emails com formato inválido
- Sistema deve validar email tanto no backend quanto no frontend
- Sistema deve tratar casos edge (null, vazio, formato incorreto)

### **Cenários de Teste**

#### 3.1 Email Válido - Formato Padrão
- **Dados de Entrada**: `"usuario@exemplo.com"`
- **Resultado Esperado**: `true`
- **Descrição**: Email com formato padrão válido

#### 3.2 Email Válido - Com Subdomínio
- **Dados de Entrada**: `"usuario@sub.exemplo.com"`
- **Resultado Esperado**: `true`
- **Descrição**: Email com subdomínio

#### 3.3 Email Inválido - Sem @
- **Dados de Entrada**: `"usuarioexemplo.com"`
- **Resultado Esperado**: `false`
- **Descrição**: Email sem símbolo @

#### 3.4 Email Inválido - Sem Domínio
- **Dados de Entrada**: `"usuario@"`
- **Resultado Esperado**: `false`
- **Descrição**: Email sem domínio após @

#### 3.5 Email Inválido - Vazio
- **Dados de Entrada**: `""`
- **Resultado Esperado**: `false`
- **Descrição**: Email vazio

#### 3.6 Email Inválido - Null
- **Dados de Entrada**: `null`
- **Resultado Esperado**: `false`
- **Descrição**: Email null

### **Implementação**
- **Backend**: Teste unitário no `UserService.isEmailValid()`
- **Frontend**: Teste unitário na função `validateEmail()`

---

## Estrutura dos Testes

### Backend (JUnit 5)
- **Localização**: `demo/src/test/java/com/demoapp/demo/`
- **Frameworks**: JUnit 5, Spring Boot Test
- **Tipos**: Testes unitários e de integração

### Frontend (Jest)
- **Localização**: `demo-front/__tests__/`
- **Frameworks**: Jest, React Testing Library
- **Tipos**: Testes unitários e de integração

---

## Critérios de Sucesso
- Todos os cenários de teste devem passar
- Cobertura de código mínima de 80%
- Testes devem ser executados em menos de 30 segundos
- Testes devem ser independentes e isolados
- Testes devem ser determinísticos (mesmo resultado sempre) 