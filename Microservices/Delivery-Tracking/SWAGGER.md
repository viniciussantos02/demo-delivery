# Documentação Swagger/OpenAPI - Delivery Tracking API

## Descrição

A API de Rastreamento de Entregas está totalmente documentada usando **OpenAPI 3.0** (Swagger). A documentação interativa permite que você explore, entenda e teste todos os endpoints da API diretamente pelo navegador.

## Como Acessar o Swagger UI

### 1. **Inicie o Serviço Delivery-Tracking**

Execute o projeto localmente:

```bash
cd /Users/viniciuspereira/Documents/dev/DemoDelivery/Microservices/Delivery-Tracking
./mvnw spring-boot:run
```

Ou via Docker:

```bash
docker-compose up
```

### 2. **Acesse o Swagger UI**

Após o serviço estar rodando, abra seu navegador e acesse:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints Disponíveis

A API está organizada sob a tag **"Delivery Management"** com os seguintes endpoints:

### ✅ Criar Entrega (Draft)
- **POST** `/api/v1/deliveries`
- Cria uma nova entrega em estado de rascunho
- **Status Code**: 201 Created

### ✏️ Editar Entrega
- **PUT** `/api/v1/deliveries/{deliveryId}`
- Edita os dados de uma entrega existente
- **Status Code**: 200 OK

### 🚗 Adicionar Courier à Entrega
- **PUT** `/api/v1/deliveries/{deliveryId}/add-delivery-courier`
- Atribui um courier específico à entrega
- **Status Code**: 200 OK

### 📋 Listar Todas as Entregas (Com Paginação)
- **GET** `/api/v1/deliveries`
- Retorna lista paginada de entregas
- **Parâmetros**: `page`, `size`, `sort`
- **Status Code**: 200 OK

### 🔍 Obter Detalhes de uma Entrega
- **GET** `/api/v1/deliveries/{deliveryId}`
- Recupera detalhes completos de uma entrega específica
- **Status Code**: 200 OK

### 📍 Colocar Entrega para Coleta (Placement)
- **POST** `/api/v1/deliveries/{deliveryId}/placement`
- Marca entrega como pronta para coleta
- **Status Code**: 200 OK

### 🎯 Realizar Coleta (Pickup)
- **POST** `/api/v1/deliveries/{deliveryId}/pickups`
- Marca entrega como coletada
- **Parâmetro**: `courierId` (opcional)
- **Status Code**: 200 OK

### ✔️ Completar Entrega
- **POST** `/api/v1/deliveries/{deliveryId}/completion`
- Marca entrega como concluída
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

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:8080/v3/api-docs.yaml`

## Arquitetura da Documentação

### Arquivos Modificados/Criados

1. **OpenApiConfig.java**
   - Arquivo de configuração centralizada
   - Define metadados da API (título, versão, descrição, contato)
   - Define servidores disponíveis (local e produção)

2. **DeliveryController.java**
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

### Criar uma Entrega (Curl)

```bash
curl -X POST http://localhost:8080/api/v1/deliveries \
  -H "Content-Type: application/json" \
  -d '{
    "origin": "Rua A, 123",
    "destination": "Rua B, 456",
    "package": {
      "weight": 2.5,
      "dimensions": "20x15x10"
    }
  }'
```

### Listar Entregas com Paginação (Curl)

```bash
curl -X GET 'http://localhost:8080/api/v1/deliveries?page=0&size=10' \
  -H "Accept: application/json"
```

### Obter Detalhes de uma Entrega (Curl)

```bash
curl -X GET http://localhost:8080/api/v1/deliveries/{deliveryId} \
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
http://gateway:8080/api/v1/deliveries/swagger-ui.html
```

## Troubleshooting

### Swagger não aparece
- Verifique se `springdoc-openapi` está no `pom.xml`
- Certifique-se de que a porta 8080 está disponível
- Verifique os logs do aplicativo

### Endpoints não aparecem no Swagger
- Confirm que o controller possui a anotação `@RestController` ou `@Controller`
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

