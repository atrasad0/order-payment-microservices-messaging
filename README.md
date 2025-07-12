# Order–Payment Microservices

Exemplo de arquitetura de microserviços em Java/Spring Boot, com comunicação assíncrona via Kafka, descoberta de serviços no Eureka e roteamento via API Gateway.

---

## 📖 Descrição

O projeto é composto por dois microserviços independentes:

1. **op-order**

   - Expõe endpoints REST para criação e consulta de pedidos.
   - Persiste pedidos em PostgreSQL.
   - Publica eventos de novo pedido no Kafka (tópico configurável).
   - Utiliza **FeignClient** para chamadas internas via API Gateway/Eureka.

2. **op-payment**

   - Consome eventos de pedido do Kafka (tópico configurável).
   - Persiste pagamento inicial em PostgreSQL e simula checkout.
   - Após aprovação, faz **chamada REST** (via FeignClient) para `op-order` para atualizar status do pedido.

Além disso, há:

- **Eureka Server** para registro e descoberta de serviços.
- **API Gateway** (Spring Cloud Gateway) para roteamento das chamadas REST externas.
- **Kafka** (via Docker Compose) como broker de mensagens.
- **PostgreSQL** para persistência de dados.

---

## ⚙️ Arquitetura

```text
Client
  |
  v
API Gateway (8765)
  |
  v
Eureka Discovery
  |
  v
op-order (Producer)
  |
  +-- publishes --> Kafka Topic (orders.new-order.topic.v1)
                     |
                     v
                op-payment (Consumer)
                     |
                     v
                 Checkout()
                     |
                     v
            FeignClient REST call
                     |
                     v
            op-order (Update payment status)
```

---

## 🛠️ Tecnologias

| Camada            | Tecnologia                           |
| ----------------- | ------------------------------------ |
| Linguagem           | Java 17                              |
| Framework         | Spring Boot 3.4.3                    |
| Messaging         | Apache Kafka (Confluent)             |
| Service Discovery | Spring Cloud Netflix Eureka 2024.0.1 |
| API Gateway       | Spring Cloud Gateway + Eureka Client |
| RPC Client        | Spring Cloud OpenFeign               |
| Persistência       | PostgreSQL                           |
| Build             | Maven                                |
| Containerização  | Docker Compose                       |

---

## 🚀 Como subir o ambiente

1. **Clone** o repositório e entre na pasta raiz:

   ```bash
   git clone https://github.com/atrasad0/order-payment-microservices-messaging.git
   ```

2. **Crie** um arquivo `.env` com as variáveis:

   ```dotenv
   # PostgreSQL 
   POSTGRES_USER=seu_usuario
   POSTGRES_PASSWORD=sua_senha
   ```

3. **Suba** os containers de broker e banco:
   ```bash
   # Vá para a pasta que contém o docker-compose
   cd <raiz-do-projeto>/docker

   # Suba apenas Kafka e PostgreSQL
   docker-compose --env-file /local_env_file/.env up -d
    ```

4. Para **cada microserviço** (`op-order`, `op-payment`), configure em sua IDE (Run Configurations) ou `.env`:
   DB_URL=jdbc:postgresql://localhost:5432/<nome_da_base>
   DB_USER=<seu_usuario>
   DB_PASSWORD=<sua_senha>
---



## 📡 Endpoints Principais

**Todas as chamadas externas devem apontar para o API Gateway** (ex.: `http://localhost:8765/`).

Os serviços são expostos em rotas aninhadas pelo nome da aplicação e versão da API. Utilize os seguintes endpoints:

### Criar Pedido (op-order)

- **POST** `http://localhost:8765/op-order/api/v1/orders`
- **Descrição:** Cria um novo pedido.
- **Body de requisição:**
  ```json
  {
    "id": null,
    "totalAmount": 270.75,
    "paymentMethod": "CREDIT_CARD",
    "paymentStatus": null,
    "orderDate": null,
    "updatedAt": null
  }
  ```
- **Resposta (201 Created):** Retorna o pedido criado com `id`, `orderDate`, `paymentStatus` e demais campos preenchidos.

### Consultar Pedido (op-order)

- **GET** `http://localhost:8765/op-order/api/v1/orders/{orderId}`
- **Descrição:** Retorna detalhes do pedido e seu status.
- **Exemplo:**
  ```bash
  curl http://localhost:8765/op-order/api/v1/orders/62
  ```

### Atualizar Status de Pedido (op-order)

- **PUT** `http://localhost:8765/op-order/api/v1/orders/{orderId}/status`
- **Descrição:** Atualiza o status de um pedido (usado internamente pelo `op-payment`).
- **Body de requisição:**
  ```json
  {
    "orderId": 62,
    "status": "PAID"
  }
  ```

### Consultar Pagamento por Pedido (op-payment)

- **GET** `http://localhost:8765/op-payment/api/v1/payments?orderId={orderId}`
- **Descrição:** Retorna informações de pagamento associadas a um `orderId`.
- **Exemplo:**
  ```bash
  curl "http://localhost:8765/op-payment/api/v1/payments?orderId=62"
  ```
---