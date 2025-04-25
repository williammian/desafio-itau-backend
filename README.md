# 📊 desafio-itau-backend

API REST em Java com Spring Boot que processa **transações financeiras** e retorna **estatísticas em tempo real** sobre as transações recebidas nos últimos 60 segundos.

---

## 🚀 Endpoints da API

### 🔹 Receber Transações
`POST /transacao`

#### Request Body (JSON)
```json
{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

- `valor` deve ser um `BigDecimal` maior ou igual a **0**.
- `dataHora` deve ser um `OffsetDateTime` no **passado** (não pode ser no futuro).
- Ambos os campos são obrigatórios.

#### Respostas
- `201 Created`: Transação aceita.
- `422 Unprocessable Entity`: Regras de validação não atendidas.
- `400 Bad Request`: Corpo da requisição inválido.

---

### 🔹 Limpar Transações
`DELETE /transacao`

- Remove todas as transações salvas.

#### Resposta
- `200 OK`: Transações removidas com sucesso.

---

### 🔹 Calcular Estatísticas
`GET /estatistica`

Retorna as estatísticas das transações dos **últimos 60 segundos**.

#### Exemplo de Resposta
```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.46,
  "min": 12.34,
  "max": 123.56
}
```

- Se **nenhuma transação** for encontrada no intervalo, todos os campos retornam `0`.

---

## ⚙️ Configurações

Valores configuráveis via `application.properties` ou variáveis de ambiente:

| Propriedade                 | Descrição                                     | Valor padrão |
|----------------------------|-----------------------------------------------|--------------|
| `SERVER_PORT`              | Porta da aplicação                            | `8080`       |
| `ESTATISTICA_SEGUNDOS`     | Janela de tempo para estatísticas (em segundos) | `60`         |

---

## 📈 Observabilidade

A API conta com **Spring Boot Actuator**, expondo métricas para Prometheus:

- `GET /actuator/health`
- `GET /actuator/metrics`
- `GET /actuator/prometheus`

---

## 🐳 Docker

### 🔧 Build e execução com Docker Compose

1. Gerar `.jar`:
   ```bash
   mvn clean package -DskipTests
   ```

2. Subir com Docker Compose:
   ```bash
   docker-compose up --build
   ```

### `Dockerfile` simples

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## ✅ Testes

Execute os testes com:

```bash
mvn test
```

Os testes cobrem cenários de:
- Criação e validação de transações
- Cálculo de estatísticas
- Regras de negócio e erro
- ControllerAdvice

---

## 📄 Documentação da API

A documentação está disponível via SpringDoc OpenAPI:

- `GET /swagger-ui/index.html` → UI Swagger
- `GET /v3/api-docs` → JSON OpenAPI 3

---

## 🔍 Monitoramento de performance

A aplicação realiza log de tempo de execução do cálculo de estatísticas, ajudando no monitoramento e otimização de performance em ambientes produtivos.

---

## 🧹 Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Web, Validation, Actuator
- Prometheus Metrics
- SpringDoc OpenAPI
- JUnit 5
- Docker / Docker Compose
- Maven

---

## 👨‍💼 Autor

William Mian  
[https://github.com/williammian](https://github.com/williammian)

---

## 📜 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

