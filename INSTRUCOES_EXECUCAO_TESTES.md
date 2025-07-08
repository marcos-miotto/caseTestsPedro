# Instruções de Execução dos Testes - SQA-25 Auth Tests

Este documento contém as instruções completas para executar os testes do projeto, tanto para o backend (Spring Boot) quanto para o frontend (Next.js).

---

## 📋 Pré-requisitos

### Backend (Spring Boot)
- Java 17 ou superior
- Maven 3.6+
- MySQL (para testes de integração)

### Frontend (Next.js)
- Node.js 18+ 
- npm ou yarn

---

## 🚀 Executando os Testes

### Backend (Spring Boot + JUnit 5)

#### 1. Navegar para o diretório do backend
```bash
cd demo
```

#### 2. Executar todos os testes
```bash
mvn test
```

#### 3. Executar testes com relatório de cobertura
```bash
mvn test jacoco:report
```

#### 4. Executar testes específicos
```bash
# Executar apenas testes de validação de senha
mvn test -Dtest=PasswordValidationTests

# Executar apenas testes de validação de email
mvn test -Dtest=EmailValidationTests

# Executar apenas testes de integração
mvn test -Dtest=AuthControllerIntegrationTests
```

#### 5. Executar testes com detalhes
```bash
mvn test -Dtest=*Tests -Dsurefire.useFile=false
```

### Frontend (Next.js + Jest)

#### 1. Navegar para o diretório do frontend
```bash
cd demo-front
```

#### 2. Instalar dependências (se necessário)
```bash
npm install
```

#### 3. Executar todos os testes
```bash
npm test
```

#### 4. Executar testes em modo watch
```bash
npm test -- --watch
```

#### 5. Executar testes com relatório de cobertura
```bash
npm test -- --coverage
```

#### 6. Executar testes específicos
```bash
# Executar apenas testes de validação de email
npm test -- email.test.ts

# Executar apenas testes de validação de senha
npm test -- password.test.ts

# Executar apenas testes do serviço de autenticação
npm test -- auth.test.ts
```

---

## 📊 Estrutura dos Testes

### Backend
```
demo/src/test/java/com/demoapp/demo/
├── service/
│   ├── PasswordValidationTests.java    # Caso de Teste 1
│   ├── EmailValidationTests.java       # Caso de Teste 3
│   └── UserServiceTests.java           # Testes existentes
├── controller/
│   └── AuthControllerIntegrationTests.java  # Caso de Teste 2
└── DemoApplicationTests.java           # Testes de contexto
```

### Frontend
```
demo-front/__tests__/
├── utils/
│   ├── email.test.ts                   # Caso de Teste 3
│   └── password.test.ts                # Caso de Teste 1
└── service/
    └── auth.test.ts                    # Caso de Teste 2
```

---

## 🎯 Casos de Teste Implementados

### Caso de Teste 1: Validação de Senha Forte
- **Backend**: `PasswordValidationTests.java`
- **Frontend**: `password.test.ts`
- **Cenários**: 15+ cenários de teste cobrindo senhas válidas, inválidas e casos edge

### Caso de Teste 2: Fluxo de Cadastro de Usuário
- **Backend**: `AuthControllerIntegrationTests.java`
- **Frontend**: `auth.test.ts`
- **Cenários**: Cadastro com dados válidos, email inválido, senha fraca, email já existente

### Caso de Teste 3: Validação de Email
- **Backend**: `EmailValidationTests.java`
- **Frontend**: `email.test.ts`
- **Cenários**: 15+ cenários de teste cobrindo emails válidos, inválidos e casos edge

---

## 🔧 Configuração do Ambiente

### Backend - application.properties
```properties
# Configuração para testes
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

### Frontend - jest.config.js
```javascript
const nextJest = require("next/jest");

const createJestConfig = nextJest({
  dir: "./",
});

const config = {
  coverageProvider: "v8",
  testEnvironment: "jsdom",
  setupFilesAfterEnv: ["<rootDir>/jest.setup.js"],
  testMatch: ["**/__tests__/**/*.test.ts", "**/__tests__/**/*.test.tsx"],
  moduleNameMapping: {
    "^@/(.*)$": "<rootDir>/$1",
  },
};

module.exports = createJestConfig(config);
```

---

## 📈 Relatórios de Cobertura

### Backend
Após executar `mvn test jacoco:report`, o relatório estará disponível em:
```
demo/target/site/jacoco/index.html
```

### Frontend
Após executar `npm test -- --coverage`, o relatório estará disponível em:
```
demo-front/coverage/lcov-report/index.html
```

---

## 🐛 Solução de Problemas

### Backend
1. **Erro de conexão com banco**: Verificar se o MySQL está rodando
2. **Erro de dependências**: Executar `mvn clean install`
3. **Testes falhando**: Verificar logs em `target/surefire-reports/`

### Frontend
1. **Erro de tipos TypeScript**: Verificar se `@types/jest` está instalado
2. **Erro de módulos**: Executar `npm install`
3. **Testes não encontrados**: Verificar se os arquivos estão em `__tests__/`

---

## 📝 Comandos Úteis

### Backend
```bash
# Limpar e recompilar
mvn clean compile

# Executar testes com debug
mvn test -X

# Executar testes específicos com debug
mvn test -Dtest=PasswordValidationTests -X
```

### Frontend
```bash
# Limpar cache do Jest
npm test -- --clearCache

# Executar testes com verbose
npm test -- --verbose

# Executar testes em paralelo
npm test -- --maxWorkers=4
```

---

## ✅ Critérios de Sucesso

- ✅ Todos os testes passando
- ✅ Cobertura de código > 80%
- ✅ Tempo de execução < 30 segundos
- ✅ Testes independentes e isolados
- ✅ Testes determinísticos

---

## 📞 Suporte

Para dúvidas ou problemas com os testes, consulte:
1. Documentação dos casos de teste: `CASOS_DE_TESTE.md`
2. Logs de execução nos diretórios `target/` e `coverage/`
3. Configurações em `pom.xml` e `jest.config.js` 