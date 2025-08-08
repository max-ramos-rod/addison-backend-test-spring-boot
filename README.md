# Addison Global Backend Technical Assessment

## Tecnologias Utilizadas

- **Java 17** (LTS)
- **Spring Boot 3.2.0** – para REST API e injeção de dependências
- **Maven 3.9.11** – gerenciamento de dependências
- **CompletableFuture** – assincronismo e não-bloqueio
- **JUnit 5** – testes unitários

## Estrutura do Projeto
```
addison-backend-test/
│
├── pom.xml
├── README.md
├── LICENSE
├── .gitignore
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── addisonglobal/
    │   │           ├── AddisonApplication.java
    │   │           │
    │   │           ├── controller/
    │   │           │   └── TokenController.java
    │   │           │
    │   │           ├── model/
    │   │           │   ├── Credentials.java
    │   │           │   ├── User.java
    │   │           │   └── UserToken.java
    │   │           │
    │   │           └── service/
    │   │               ├── AuthService.java
    │   │               ├── TokenService.java
    │   │               └── TokenServiceImpl.java
    │   │
    │   └── resources/
    │
    └── test/
        └── java/
            └── com/
                └── addisonglobal/
                    └── service/
                        └── TokenServiceImplTest.java
```
## Requisitos de Negócio Implementados

1. **Autenticação**:
   - Credenciais válidas apenas se `password == username.toUpperCase()`
   - Retardo aleatório (0–5000ms) simulado
2. **Emissão de Token**:
   - Formato: `userId_timestampZ` (ex: `house_2025-08-08T10:00:00Z`)
   - Falha se `userId` começa com 'A'
   - Retardo aleatório (0–5000ms)
3. **Orquestração Assíncrona**:
   - Uso de `CompletableFuture` para não bloquear threads
   - Composição com `.thenApply` e `.exceptionally`
   - Tratamento de falhas isolado

## Como Executar

### Pré-requisitos
- JDK 17 instalado
- Maven 3.9+ instalado

### Passos
```bash
# Clonar ou extrair o projeto
cd addison-backend-test

# Compilar e executar
mvn spring-boot:run
```