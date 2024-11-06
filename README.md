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

3. Configure o MongoDB no `application.properties`:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/assinaturasdb
    ```

4. Execute o projeto:
    ```bash
    mvn spring-boot:run
    ```

A API estará disponível em `http://localhost:8080`.

## Estrutura do Projeto

### Classes Principais

#### `Plano`
Representa os planos de assinatura disponíveis. Os atributos incluem:
- `id`: Identificador do plano.
- `nome`: Nome do plano (ex.: Básico, Premium).
- `preco`: Preço mensal do plano.
- `beneficios`: Descrição dos benefícios do plano.
- `dispositivosSimultaneos`: Número máximo de dispositivos que podem usar o plano ao mesmo tempo.

#### `Usuario`
Representa os usuários da API e contém as seguintes informações:
- `id`: Identificador do usuário.
- `email`: Email do usuário.
- `planoAtivo`: Plano atualmente ativo do usuário.
- `planoAtivoStatus`: Indica se o plano do usuário está ativo ou cancelado.

#### `HistoricoAlteracaoPlano`
Armazena o histórico de alterações de planos para cada usuário:
- `id`: Identificador do histórico.
- `email`: Email do usuário.
- `planoAnterior`: Nome do plano anterior.
- `planoAtual`: Nome do plano atual.
- `historicoPlanos`: Lista dos planos que o usuário já teve.
- `dataAlteracao`: Data e hora da última alteração de plano.
- `tipoAlteracao`: Tipo da alteração (UPGRADE, DOWNGRADE, CANCELAMENTO).
- `motivoCancelamento`: Motivo informado pelo usuário no cancelamento do plano.

## Endpoints

### 1. Planos

#### Criar Plano
- **Endpoint**: `POST /api/planos`
- **Descrição**: Cria um novo plano.
- **Request Body**:
    ```json
    {
      "nome": "Plano Premium",
      "preco": 29.99,
      "beneficios": "Acesso ilimitado a todos os conteúdos",
      "dispositivosSimultaneos": 3
    }
    ```
- **Validações**: 
    - `nome` não pode ser vazio.
    - `preco` deve ser positivo.
    - `beneficios` não pode ser vazio.
    - `dispositivosSimultaneos` deve ser maior que zero.

#### Listar Planos
- **Endpoint**: `GET /api/planos`
- **Descrição**: Retorna a lista de todos os planos disponíveis.

#### Buscar Plano por ID
- **Endpoint**: `GET /api/planos/{id}`
- **Descrição**: Retorna os detalhes de um plano específico.
- **Resposta**:
    ```json
    {
      "id": "123",
      "nome": "Plano Gold",
      "preco": 39.99,
      "beneficios": "Acesso premium com dispositivos ilimitados",
      "dispositivosSimultaneos": 5
    }
    ```

#### Atualizar Plano
- **Endpoint**: `PUT /api/planos/{id}`
- **Descrição**: Atualiza um plano existente.
- **Request Body**:
    - Deve conter apenas os campos a serem alterados, por exemplo:
    ```json
    {
      "preco": 49.99
    }
    ```
- **Validações**:
    - Campos atualizados devem seguir as regras de validação de criação.

#### Deletar Plano
- **Endpoint**: `DELETE /api/planos/{id}`
- **Descrição**: Remove um plano do sistema.

### 2. Usuários

#### Consultar Plano do Usuário
- **Endpoint**: `GET /api/usuarios`
- **Descrição**: Retorna o plano ativo do usuário.
- **Cabeçalho de Autorização**: É necessário enviar um token de autenticação no cabeçalho `Authorization`.

#### Visualizar Histórico de Alterações
- **Endpoint**: `GET /api/usuarios/historico`
- **Descrição**: Retorna o histórico de alterações de plano de um usuário.
- **Cabeçalho de Autorização**: É necessário enviar um token de autenticação no cabeçalho `Authorization`.
- **Resposta**:
    ```json
    {
      "email": "usuario@exemplo.com",
      "planoAnterior": "Básico",
      "planoAtual": "Premium",
      "historicoPlanos": [
        {
          "nome": "Básico",
          "preco": 15.90,
          "beneficios": "Qualidade de imagem HD, 1 tela simultânea e anuncios",
          "dispositivosSimultaneos": 1
        },
        {
          "nome": "Premium",
          "preco": 45.90,
          "beneficios": "Qualidade de imagem 4K, 4 telas simultâneas e sem anuncios",
          "dispositivosSimultaneos": 4
        }
      ],
      "dataAlteracao": "2023-10-05T14:48:00",
      "tipoAlteracao": "UPGRADE",
      "motivoCancelamento": null
    }
    ```

#### Associar Plano a Usuário
- **Endpoint**: `POST /api/usuarios/associar-plano/{idPlano}`
- **Descrição**: Associa um novo plano ao usuário autenticado.
- **Cabeçalho de Autorização**: Token no cabeçalho `Authorization`.
- **Processo de Associação**:
    - Adiciona o novo plano ao usuário e atualiza o histórico de alterações.
    - Define o tipo de alteração como `UPGRADE`, `DOWNGRADE` ou `ASSOCIAÇÃO`, dependendo do preço do novo plano em comparação com o plano anterior.
- **Resposta**:
    ```json
    {
      "id": "456",
      "email": "usuario@exemplo.com",
      "planoAtivo": {
        "nome": "Premium",
        "preco": 45.90,
        "beneficios": "Qualidade de imagem 4K, 4 telas simultâneas e sem anuncios",
        "dispositivosSimultaneos": 4
      },
      "planoAtivoStatus": true
    }
    ```

#### Cancelar Plano do Usuário
- **Endpoint**: `POST /api/usuarios/cancelar-plano`
- **Descrição**: Cancela o plano ativo do usuário.
- **Cabeçalho de Autorização**: Token no cabeçalho `Authorization`.
- **Request Body**:
    ```json
    {
      "motivo": "Problemas financeiros"
    }
    ```
- **Processo de Cancelamento**:
    - Define o `planoAtivoStatus` do usuário como `false`.
    - Atualiza o histórico de alterações com o tipo de alteração `CANCELAMENTO` e armazena o motivo do cancelamento.

## Regras de Negócio e Validações

1. **Validação de Dados**:
    - Todos os campos obrigatórios de `Plano` devem ser validados, garantindo que não sejam nulos ou inválidos.
2. **Tipos de Alteração no Histórico**:
    - `UPGRADE`: Se o novo plano tiver um preço maior que o plano anterior.
    - `DOWNGRADE`: Se o novo plano tiver um preço menor.
    - `ASSOCIAÇÃO`: Se for a primeira vez que o plano está sendo associado ao usuário.
    - `CANCELAMENTO`: Quando o usuário cancela seu plano ativo.
3. **Motivo do Cancelamento**: Caso o usuário não informe o motivo do cancelamento, ele será registrado como "Motivo não informado".

## Estrutura de Dados

### Plano
```json
{
  "id": "string",
  "nome": "string",
  "preco": "number",
  "beneficios": "string",
  "dispositivosSimultaneos": "integer"
}
```

### Usuario
```json
{
  "id": "string",
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

### Histórico de Alteração de Plano
```json
{
  "id": "string",
  "email": "string",
  "planoAnterior": "string", // Ex.: "Básico", "Padrão", "Premium"
  "planoAtual": "string", // Ex.: "Básico", "Padrão", "Premium"
  "historicoPlanos": [
    {
      "id": "string",
      "nome": "string",
      "preco": "number",
      "beneficios": "string",
      "dispositivosSimultaneos": "integer"
    }
  ],
  "dataAlteracao": "LocalDateTime",
  "tipoAlteracao": "string", // Ex.: "UPGRADE", "DOWNGRADE", "CANCELAMENTO", "ASSOCIAÇÃO"
  "motivoCancelamento": "string" // Ex.: "Mudança de plano", "Insatisfação com o serviço", "Problemas financeiros"
}
```
## Lógica de Funcionamento
### Serviços e Validações
- Criação de Plano: Antes de criar um plano, a API valida se os dados fornecidos são válidos (por exemplo, nome, preço e benefícios não podem ser vazios ou inválidos).
- Associação de Plano a Usuário: Quando um plano é associado a um usuário, o histórico de alterações é atualizado com o novo plano e o tipo de alteração (se houve um UPGRADE, DOWNGRADE, ou uma ASSOCIAÇÃO). Caso o plano anterior tenha um preço inferior ao novo plano, é registrado um UPGRADE, e se for superior, é registrado um DOWNGRADE.
- Cancelamento de Plano: Quando um plano é cancelado, o histórico do usuário é atualizado com a data do cancelamento, o plano atual é marcado como "N/A", e o motivo do cancelamento é registrado.
### Exemplo de Fluxo de Uso
1. Um usuário é criado no sistema e associado a um plano (por exemplo, o "Plano Padrão").
2. O usuário decide mudar para um plano superior, o "Plano Premium". Isso é registrado como um UPGRADE no histórico do usuário.
3. O usuário decide cancelar seu plano ativo, o que é registrado no histórico como um CANCELAMENTO com o motivo fornecido (ex: "preço alto").
