# 🚚 DemoDelivery

Um repositório educacional para estudar **Domain-Driven Design (DDD)** em uma arquitetura de **microserviços**. Este projeto simula um sistema de gerenciamento de entregas com comunicação assíncrona entre múltiplos microserviços, implementando padrões modernos de desenvolvimento.

## 📋 Visão Geral do Projeto

O **DemoDelivery** é uma aplicação que demonstra uma arquitetura de microserviços completa para um sistema de delivery. A aplicação foi projetada para simular o fluxo de uma entrega, desde sua criação até sua conclusão, com gerenciamento de entregadores (couriers) e rastreamento em tempo real.

### O que é o Projeto?

Um **sistema de gerenciamento de entregas** que simula o processo completo de uma entrega com os seguintes componentes:

- **Rastreamento de Entregas**: Gerencia pedidos de entrega, itens, transições de status e rastreamento
- **Gerenciamento de Entregadores**: Administra entregadores, suas informações e atribuições de entregas
- **API Gateway**: Ponto de entrada único para todas as requisições do cliente
- **Service Registry**: Registro e descoberta dinâmica de serviços
- **Infraestrutura de Mensageria**: Comunicação assíncrona entre serviços

### Para que Serve?

Este projeto serve como uma referência prática e educacional para:

1. **Aprender DDD (Domain-Driven Design)** com exemplos reais
2. **Entender Microserviços** com padrões de comunicação síncrona e assíncrona
3. **Implementar Padrões de Resiliência** com Circuit Breaker e Retry
4. **Trabalhar com Eventos de Domínio** para comunicação entre serviços
5. **Usar Docker e Docker Compose** para ambiente local
6. **Implementar Service Discovery** com Eureka

## 🏗️ Arquitetura do Projeto

### Visão Geral da Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                     CLIENTE / FRONTEND                       │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP
                         ▼
            ┌────────────────────────┐
            │   API Gateway (9999)   │
            │  Spring Cloud Gateway  │
            └──────┬──────────┬──────┘
                   │          │
         ┌─────────┘          └─────────┐
         │                               │
         ▼                               ▼
  ┌─────────────────┐        ┌──────────────────┐
  │ Delivery Track  │        │ Courier Mgmt     │
  │    (8080)       │        │    (8081)        │
  │  PostgreSQL     │        │  PostgreSQL      │
  └────────┬────────┘        └────────┬─────────┘
           │                          │
           └──────────┬───────────────┘
                      │
                      ▼
          ┌─────────────────────────┐
          │   Kafka Message Broker   │
          │  (Event-Driven Arch)     │
          └─────────────────────────┘
                      │
           ┌──────────┴──────────┐
           │                     │
           ▼                     ▼
  ┌─────────────────┐  ┌─────────────────┐
  │ PostgreSQL DB   │  │   Eureka Server │
  │    (5432)       │  │     (8761)      │
  │                 │  │  Service Registry
  └─────────────────┘  └─────────────────┘
           │
           ▼
  ┌─────────────────┐
  │   PgAdmin       │
  │    (8083)       │
  └─────────────────┘
```

### Microserviços

#### 1. **Delivery-Tracking Service** (Porta 8080)
- Responsável pelo gerenciamento completo de entregas
- Cria, atualiza e rastreia entregas
- Gerencia itens dentro de uma entrega
- **Banco de Dados**: PostgreSQL (deliverydb)
- **Padrão DDD**: Implementa Aggregate `Delivery` com entidades `Item`
- **Publicador de Eventos**: Publica eventos de domínio para Kafka

#### 2. **Courier-Management Service** (Porta 8081)
- Gerencia informações de entregadores (couriers)
- Atribui entregas aos entregadores
- Calcula pagamentos e comissões
- **Banco de Dados**: PostgreSQL (courierdb)
- **Consumidor de Eventos**: Consome eventos de entrega do Kafka
- **Cliente REST**: Comunica com Delivery-Tracking Service

#### 3. **API Gateway** (Porta 9999)
- Ponto de entrada único para todas as requisições
- Roteamento inteligente baseado em padrões de URL
- **Resiliência**: Implementa Circuit Breaker e Retry
- **Load Balancing**: Usa Eureka para descoberta de serviços
- **Filtros Customizados**: Remove campos sensíveis de respostas

#### 4. **Service Registry (Eureka)** (Porta 8761)
- Servidor de registro e descoberta de serviços
- Permite que os microserviços se registrem dinamicamente
- Viabiliza o roteamento dinâmico do API Gateway

### Infraestrutura de Suporte

- **PostgreSQL**: Banco de dados relacional para persistência
- **Kafka**: Message broker para comunicação assíncrona
- **Kafka UI**: Interface web para gerenciar Kafka
- **PgAdmin**: Interface web para gerenciar PostgreSQL

## 🎯 Padrões de Projeto Implementados

### 1. **Domain-Driven Design (DDD)**

#### Componentes DDD Implementados:

- **Agregados**: `Delivery` é um agregado que contém múltiplas `Item`s
  - Garante consistência das regras de negócio
  - Apenas o agregado pode modificar seus elementos internos
  
- **Entidades**: Objetos com identidade única (ID)
  - `Delivery`: Entidade raiz do agregado
  - `Item`: Entidades dentro do agregado
  
- **Value Objects**: Objetos imutáveis que representam valores
  - Encapsulam comportamento e validação
  - Comparação por valor, não por identidade
  
- **Eventos de Domínio**: Representam fatos importantes que ocorreram no domínio
  - `DeliveryPlacedEvent`: Quando uma entrega é criada
  - `DeliveryPickedUpEvent`: Quando uma entrega é coletada
  - `DeliveryFulfilledEvent`: Quando uma entrega é concluída
  
- **Repositories**: Abstraem a persistência das agregados
  - Interface com a camada de dados
  - Permite testes mais fáceis
  
- **Domain Services**: Lógica de negócio que não pertence a uma entidade específica
  - Cálculos de tarifas
  - Lógica de atribuição de entregadores
  
- **Exceções de Domínio**: Erros específicos do domínio de negócio
  - `DomainException`: Base para todas as exceções de domínio

### 2. **Arquitetura de Microserviços**

- **Separação de Responsabilidades**: Cada serviço tem um contexto limitado (Bounded Context)
- **Comunicação Assíncrona**: Uso de eventos via Kafka
- **Independência de Banco de Dados**: Cada serviço tem seu próprio banco de dados
- **Descoberta de Serviços**: Eureka para registro dinâmico

### 3. **Padrões de Resiliência**

#### Circuit Breaker (Resilience4j)
```yaml
- Configuração COUNT_BASED: 10 chamadas monitoradas
- Taxa de falha: 50% dispara abertura do circuito
- Estados: CLOSED → OPEN → HALF_OPEN → CLOSED
- Timeout: 10 segundos (padrão), 5 segundos (Courier API)
```

#### Retry Pattern (Resilience4j)
```yaml
- Tentativas padrão: 2
- Tentativas para Courier API: 3
- Espera entre tentativas: 20ms
- Apenas em exceções específicas
```

#### Timeout no API Gateway
```yaml
- Connection Timeout: 10ms
- Response Timeout: 300ms
```

### 4. **Event-Driven Architecture**

- **Publicador de Eventos**: Delivery-Tracking publica eventos para Kafka
- **Consumidor de Eventos**: Courier-Management consome eventos
- **Event Mapping**: Mapeamento automático de eventos para classes Java
- **Desacoplamento**: Serviços não precisam conhecer uns aos outros

### 5. **API Gateway Pattern**

- **Roteamento**: Direciona requisições baseado em padrões de path
- **Filtros**: Remove dados sensíveis das respostas
- **Rate Limiting e Resiliência**: Implementado no gateway
- **Rewrite Path**: Transforma URLs públicas em internas

## 💻 Tecnologias Utilizadas

### Linguagem & Framework
- **Java 21**: Linguagem de programação
- **Spring Boot 3.5.9**: Framework principal para criação de aplicações
- **Spring Cloud 2025.0.0**: Ferramentas para microsserviços

### Persistência de Dados
- **PostgreSQL**: Banco de dados relacional
- **Spring Data JPA**: ORM e abstração de dados
- **Hibernate**: Provider JPA

### Comunicação entre Serviços
- **Spring Cloud Gateway**: API Gateway
- **Spring Cloud Netflix Eureka**: Service Registry e Discovery
- **Apache Kafka**: Message Broker para comunicação assíncrona
- **Spring Kafka**: Integração com Kafka

### Resiliência & Tolerância a Falhas
- **Resilience4j 2.3.0**: Padrões de circuit breaker e retry
- **Spring Boot AOP**: Aspect-Oriented Programming para aplicar padrões

### Utilitários
- **Lombok**: Redução de boilerplate (getters, setters, construtores)
- **Spring Validation**: Validação de dados
- **REST Assured**: Testes de APIs REST

### Build & Containerização
- **Maven**: Gerenciador de dependências e build
- **Docker**: Containerização de aplicações
- **Docker Compose**: Orquestração de containers locais

### Ferramentas de Desenvolvimento
- **PgAdmin**: Interface web para PostgreSQL
- **Kafka UI**: Interface web para Kafka

## 🚀 Guia de Inicialização

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21**: [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+**: [Download](https://maven.apache.org/download.cgi)
- **Docker**: [Download](https://www.docker.com/products/docker-desktop)
- **Docker Compose**: Geralmente incluído com Docker Desktop

### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/viniciussantos02/demo-delivery.git
cd DemoDelivery
```

### Passo 2: Iniciar Infraestrutura com Docker Compose

Este comando inicia todos os serviços de infraestrutura necessários:

```bash
docker-compose up -d
```

**Serviços iniciados:**
- **PostgreSQL** (porta 5432): Banco de dados
  - Usuário: `postgres`
  - Senha: `postgres`
  
- **PgAdmin** (porta 8083): Interface web para PostgreSQL
  - URL: http://localhost:8083
  - Email: `dba@demodelivery.com`
  - Senha: `demodelivery`
  
- **Kafka** (porta 9092): Message broker
  - Bootstrap servers: `localhost:9092`
  
- **Kafka UI** (porta 8084): Interface web para Kafka
  - URL: http://localhost:8084

### Passo 3: Iniciar os Microserviços

Abra 4 terminais separados e execute os comandos a seguir:

#### Terminal 1: Service Registry (Eureka)
```bash
cd Microservices/Service-Registry
mvn spring-boot:run
# Disponível em: http://localhost:8761
```

#### Terminal 2: Delivery-Tracking Service
```bash
cd Microservices/Delivery-Tracking
mvn spring-boot:run
# Disponível em: http://localhost:8080
```

#### Terminal 3: Courier-Management Service
```bash
cd Microservices/Courier-Management
mvn spring-boot:run
# Disponível em: http://localhost:8081
```

#### Terminal 4: API Gateway
```bash
cd Microservices/Gateway
mvn spring-boot:run
# Disponível em: http://localhost:9999
```

### Passo 4: Verificar Status

- **Service Registry (Eureka)**: http://localhost:8761
  - Verifique se todos os serviços foram registrados
  
- **Kafka UI**: http://localhost:8084
  - Veja os tópicos e mensagens

- **PgAdmin**: http://localhost:8083
  - Gerenciar bancos de dados

### Passo 5: Testar os Endpoints

Use **Postman**, **Thunder Client** ou **curl** para testar:

```bash
# Exemplo: Listar entregadores via API Gateway
curl -X GET http://localhost:9999/api/v1/couriers

# Exemplo: Listar entregas via API Gateway
curl -X GET http://localhost:9999/api/v1/deliveries
```

## 📊 Fluxo de Dados

### Criação de uma Entrega

```
Cliente 
  ▼
API Gateway (9999)
  ▼
Delivery-Tracking Service (8080)
  - Cria agregado Delivery
  - Valida regras de negócio
  - Publica DeliveryPlacedEvent
  ▼
Kafka (Topic: delivery-events)
  ▼
Courier-Management Service (8081)
  - Consome DeliveryPlacedEvent
  - Atualiza informações de entregadores
  - Disponibiliza entrega para atribuição
```

### Atribuição de Entrega

```
Cliente 
  ▼
API Gateway (9999)
  ▼
Courier-Management Service (8081)
  - Atribui entrega a um entregador
  - Chama API do Delivery-Tracking (com Circuit Breaker + Retry)
  ▼
Delivery-Tracking Service (8080)
  - Atualiza status da entrega
  - Publica DeliveryPickedUpEvent
  ▼
Kafka
  ▼
Courier-Management (recebe confirmação)
```

## 🔄 Ciclo de Vida de uma Entrega

```
DRAFT (Rascunho)
  ▼
PLACED (Criada) → DeliveryPlacedEvent publicado
  ▼
PICKED_UP (Coletada) → DeliveryPickedUpEvent publicado
  ▼
FULFILLED (Entregue) → DeliveryFulfilledEvent publicado
```

## 📦 Estrutura de Diretórios

```
DemoDelivery/
├── docker-compose.yml              # Configuração de infraestrutura
├── README.md                        # Este arquivo
├── Docs/                            # Documentação
└── Microservices/
    ├── Service-Registry/            # Servidor Eureka
    │   ├── pom.xml
    │   └── src/
    │       └── main/
    │           ├── java/
    │           └── resources/
    │               └── application.yaml
    ├── Gateway/                     # API Gateway
    │   ├── pom.xml
    │   └── src/
    │       └── main/
    │           ├── java/
    │           └── resources/
    │               └── application.yaml
    ├── Delivery-Tracking/           # Serviço de Rastreamento
    │   ├── pom.xml
    │   └── src/
    │       ├── main/
    │       │   ├── java/
    │       │   │   └── com/demoworks/demodelivery/
    │       │   │       └── delivery/
    │       │   │           ├── domain/         # Camada de Domínio (DDD)
    │       │   │           ├── application/    # Casos de uso
    │       │   │           ├── presentation/   # Controllers REST
    │       │   │           └── infrastructure/ # Implementações técnicas
    │       │   └── resources/
    │       │       └── application.yml
    │       └── test/
    └── Courier-Management/          # Serviço de Gerenciamento de Couriers
        ├── pom.xml
        └── src/
            ├── main/
            │   ├── java/
            │   │   └── com/demoworks/demodelivery/
            │   │       └── courier/
            │   │           ├── domain/
            │   │           ├── application/
            │   │           ├── presentation/
            │   │           └── infrastructure/
            │   └── resources/
            │       └── application.yml
            └── test/
```

## 🧪 Testando a Aplicação

### Exemplo de Fluxo Completo com curl

1. **Listar Entregadores**:
```bash
curl -X GET http://localhost:9999/api/v1/couriers
```

2. **Criar uma Entrega** (via Delivery-Tracking diretamente):
```bash
curl -X POST http://localhost:8080/api/v1/deliveries \
  -H "Content-Type: application/json" \
  -d '{
    "recipientName": "João Silva",
    "recipientAddress": "Rua A, 123",
    "recipientCity": "São Paulo",
    "distanceFee": 10.00
  }'
```

3. **Atribuir Entrega a um Entregador** (via Courier-Management):
```bash
curl -X POST http://localhost:8081/api/v1/couriers/{courierId}/deliveries/{deliveryId}
```

## 🐛 Troubleshooting

### Porto já em uso
```bash
# Encontre o processo usando a porta
lsof -i :9999

# Mate o processo
kill -9 <PID>
```

### PostgreSQL não conecta
```bash
# Verifique se o container está rodando
docker ps | grep postgres

# Veja os logs
docker logs <container_id>
```

### Kafka não consome mensagens
- Verifique no Kafka UI (http://localhost:8084)
- Verifique se o tópico `delivery-events` existe
- Verifique o `group-id` no application.yml

### Serviço não aparece no Eureka
- Aguarde 30 segundos (Eureka leva tempo para registrar)
- Verifique logs do serviço
- Confirme que `eureka.client.service-url.default-zone` está correto

## 📚 Conceitos Importantes

### DDD (Domain-Driven Design)
Uma abordagem que coloca o domínio de negócio no centro do design de software, utilizando:
- Linguagem ubíqua entre desenvolvedores e especialistas de negócio
- Separação clara entre lógica de negócio e técnica
- Modelos ricos que refletem o domínio

### Bounded Context
Limite explícito dentro do qual o modelo de domínio é definido e aplicável:
- Delivery-Tracking: Contexto de rastreamento de entregas
- Courier-Management: Contexto de gerenciamento de entregadores

### Event Sourcing
Armazenar mudanças no estado como uma série de eventos imutáveis (implementado via Kafka)

### Circuit Breaker
Padrão que previne que uma aplicação tente executar operações que provavelmente falharão:
- CLOSED: Operações normais
- OPEN: Bloqueia chamadas
- HALF_OPEN: Testa se a dependência se recuperou

## 🤝 Contribuindo

Este é um projeto educacional. Sinta-se à vontade para:
- Fazer fork
- Criar branches para novas features
- Enviar pull requests
- Reportar issues

## 👨‍💻 Autor

Vinicius Pereira

---

**Última atualização**: 2026-04-08
