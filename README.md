# API de Gerenciamento de Planos de Assinatura

Esta API gerencia planos de assinatura e associações de usuários, oferecendo funcionalidades de CRUD para planos, associação e cancelamento de planos de usuários, e registro de histórico de alterações de planos.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data MongoDB**
- **MongoDB**
- **Maven**

## Instalação
1. Clone o repositório:
```bash
git clone https://github.com/mmp052/projeto_zambom_gp2.git
```

2. Navegue até o diretório do projeto:
```bash
cd projeto_zambom_gp2
```

3. Configure o MongoDB no application.properties:
```propeties
spring.data.mongodb.uri=mongodb://localhost:27017/assinaturasdb
```

4. Execute o projeto:
```bash
mvn spring-boot:run
```

A API estará disponível em http://localhost:8080.

## Endpoints

### 1. Planos

#### Criar Plano

- **Endpoint: POST /api/planos**
- **Descrição: Cria um novo plano.**
- **Exemplo de Request Body:**
```json
{
  "nome": "Plano Premium",
  "preco": 29.99,
  "beneficios": "Acesso ilimitado a todos os conteúdos",
  "dispositivosSimultaneos": 3
}
```

#### Listar Planos

- **Endpoint: GET /api/planos**
- **Descrição: Retorna a lista de todos os planos disponíveis.**

#### Buscar Plano por ID

- **Endpoint: GET /api/planos/{id}**
- **Descrição: Retorna os detalhes de um plano específico.**

#### Atualizar Plano

- **Endpoint: PUT /api/planos/{id}**
- **Descrição: Atualiza um plano existente.**
- **Exemplo de Request Body:**
```json
{
  "nome": "Plano Gold",
  "preco": 39.99,
  "beneficios": "Acesso premium com dispositivos ilimitados",
  "dispositivosSimultaneos": 5
}
```

#### Deletar Plano

- **Endpoint: DELETE /api/planos/{id}**
- **Descrição: Remove um plano do sistema.**

### 2. Usuários

#### Associar Plano a Usuário

- **Endpoint: POST /api/usuarios/{usuarioId}/associar-plano**
- **Descrição: Associa um novo plano a um usuário.**
- **Exemplo de Request Body:**
```json
{
  "nome": "Plano Standard",
  "preco": 19.99,
  "beneficios": "Acesso a conteúdos selecionados",
  "dispositivosSimultaneos": 2
}
```

#### Cancelar Plano de Usuário

- **Endpoint: POST /api/usuarios/{usuarioId}/cancelar-plano**
- **Descrição: Cancela a associação de um plano para um usuário.**
- **Parâmetro:**
    - **motivo:** Motivo do cancelamento (ex: "insatisfação", "preço alto").
- **Exemplo de Request:**
```bash
POST /api/usuarios/{usuarioId}/cancelar-plano?motivo=preço alto
```

### Estrutura de Dados

#### Plano
```json
{
  "id": "string",
  "nome": "string",
  "preco": "number",
  "beneficios": "string",
  "dispositivosSimultaneos": "integer"
}
```

#### Usuário
```json
{
  "id": "string",
  "nome": "string",
  "email": "string",
  "planoAtivo": {
    "id": "string",
    "nome": "string",
    "preco": "number",
    "beneficios": "string",
    "dispositivosSimultaneos": "integer"
  },
  "planoAtivoStatus": "boolean"
}
```