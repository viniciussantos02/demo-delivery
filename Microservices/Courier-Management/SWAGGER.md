# Documentação Swagger/OpenAPI - Courier Management API

## Descrição

A API de Gerenciamento de Couriers está totalmente documentada usando **OpenAPI 3.0** (Swagger). A documentação interativa permite que você explore, entenda e teste todos os endpoints da API diretamente pelo navegador.

## Como Acessar o Swagger UI

### 1. **Inicie o Serviço Courier-Management**

Execute o projeto localmente:

```bash
cd /Users/viniciuspereira/Documents/dev/DemoDelivery/Microservices/Courier-Management
./mvnw spring-boot:run
```

Ou via Docker:

```bash
docker-compose up
```

### 2. **Acesse o Swagger UI**

Após o serviço estar rodando, abra seu navegador e acesse:

```
http://localhost:8081/swagger-ui.html
```

## Endpoints Disponíveis

A API está organizada sob a tag **"Courier Management"** com os seguintes endpoints:

### ✅ Registrar Novo Courier
- **POST** `/api/v1/couriers`
- Registra um novo courier no sistema
- **Status Code**: 201 Created

### ✏️ Atualizar Dados do Courier
- **PUT** `/api/v1/couriers/{courierId}`
- Atualiza as informações de um courier existente
- **Status Code**: 200 OK

### 📋 Listar Todos os Couriers (Com Paginação)
- **GET** `/api/v1/couriers`
- Retorna lista paginada de couriers
- **Parâmetros**: `page`, `size`, `sort`
- **Status Code**: 200 OK

### 🔍 Obter Detalhes de um Courier
- **GET** `/api/v1/couriers/{courierId}`
- Recupera informações completas de um courier específico
- **Status Code**: 200 OK

### 💰 Calcular Pagamento do Courier
- **POST** `/api/v1/couriers/payout-calculation`
- Calcula o valor do pagamento baseado na distância em km
- **Body**: `{ "distanceInKm": 10.5 }`
- **Status Code**: 200 OK

## Recursos do Swagger UI

### 🧪 Teste Interativo
- Clique em qualquer endpoint para expandi-lo
- Visualize os parâmetros necessários
- Edite os valores e clique em **"Try it out"**
- Veja a resposta em tempo real

### 📊 Schemas
- Visualize a estrutura de dados (schemas) dos objetos
- Entenda os tipos de dados e validações

### 📥 Requisições de Exemplo
- Cada endpoint mostra um exemplo de requisição
- Copie e cole em sua ferramenta favorita (Postman, curl, etc.)

### 🔗 Links Úteis no Swagger UI

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:8081/v3/api-docs.yaml`

## Arquitetura da Documentação

### Arquivos Modificados/Criados

1. **OpenApiConfig.java**
   - Arquivo de configuração centralizada
   - Define metadados da API (título, versão, descrição, contato)
   - Define servidores disponíveis (local e produção)
   - Localização: `infrastructure/config/`

2. **CourierController.java**
   - Anotações `@Tag` para agrupar endpoints
   - Anotações `@Operation` para descrever cada operação
   - Anotações `@ApiResponses` para documentar possíveis respostas HTTP
   - Anotações `@Parameter` para descrever parâmetros

3. **pom.xml**
   - Adicionada dependência `springdoc-openapi-starter-webmvc-ui` v2.6.0

4. **application.yml**
   - Configurações do SpringDoc OpenAPI
   - Definição do caminho do Swagger UI e docs

## Exemplos de Uso

### Registrar um Novo Courier (Curl)

```bash
curl -X POST http://localhost:8081/api/v1/couriers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João da Silva",
    "email": "joao@example.com",
    "phone": "+5511987654321",
    "vehicleType": "motorcycle"
  }'
```

### Listar Couriers com Paginação (Curl)

```bash
curl -X GET 'http://localhost:8081/api/v1/couriers?page=0&size=10' \
  -H "Accept: application/json"
```

### Calcular Pagamento do Courier (Curl)

```bash
curl -X POST http://localhost:8081/api/v1/couriers/payout-calculation \
  -H "Content-Type: application/json" \
  -d '{
    "distanceInKm": 15.5
  }'
```

### Obter Detalhes de um Courier (Curl)

```bash
curl -X GET http://localhost:8081/api/v1/couriers/{courierId} \
  -H "Accept: application/json"
```

## Configurações Adicionais

Se você deseja customizar o Swagger, edite o arquivo `application.yml`:

```yaml
springdoc:
  swagger-ui:
    path: /swagger-ui.html           # Caminho do Swagger UI
    enabled: true                     # Ativar/desativar Swagger
    tagsSorter: alpha                 # Ordenação de tags
    operationsSorter: alpha           # Ordenação de operações
  api-docs:
    path: /v3/api-docs               # Caminho do documento OpenAPI
  show-actuator: false               # Ocultar endpoints do actuator
```

## Integração com Gateway

Quando estiver em produção via Gateway:

```
http://gateway:8080/api/v1/couriers/swagger-ui.html
```

## Troubleshooting

### Swagger não aparece
- Verifique se `springdoc-openapi` está no `pom.xml`
- Certifique-se de que a porta 8081 está disponível
- Verifique os logs do aplicativo

### Endpoints não aparecem no Swagger
- Confirm que o controller possui a anotação `@RestController`
- Verifique se os métodos possuem anotações `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.

### Modelos não aparecem com schemas completos
- Adicione getters/setters nas classes de modelo
- Use anotações Lombok (`@Data`, `@Getter`, `@Setter`)

## Documentação Oficial

- **SpringDoc OpenAPI**: https://springdoc.org/
- **OpenAPI 3.0 Specification**: https://spec.openapis.org/oas/v3.0.0
- **Swagger UI**: https://swagger.io/tools/swagger-ui/

---

**Criado em**: Desenvolvimento
**Versão da API**: 1.0.0
**Versão do Spring Boot**: 3.5.9
**Porta**: 8081

