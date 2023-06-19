# desafio-klok

# API de Vendas

Esta API de vendas permite criar, listar, buscar e excluir vendas. A API foi desenvolvida usando o framework Spring Boot e o banco de dados PostgreSQL.

## Pré-requisitos

Certifique-se de ter os seguintes requisitos instalados em sua máquina de desenvolvimento:

- Java Development Kit (JDK) 17
- Apache Maven 3.8.3
- Docker (opcional, apenas se você deseja executar o aplicativo em contêineres Docker)
- Um cliente de API (por exemplo, Postman) para testar as chamadas de API

## Configuração do Banco de Dados

Esta api usa um banco de dados PostgreSQL. Antes de executar o aplicativo, você precisa configurar um banco de dados PostgreSQL e fornecer as informações de conexão no arquivo `docker-compose.yml` e no arquivo de propriedades do aplicativo (`application.properties`), conforme explicado abaixo:

1. Abra o arquivo `docker-compose.yml` e atualize as variáveis de ambiente no serviço `postgres` de acordo com suas preferências:

   - `POSTGRES_USER`: O nome de usuário do banco de dados (padrão: root).
   - `POSTGRES_PASSWORD`: A senha do banco de dados (padrão: 123456).
   - `POSTGRES_DB`: O nome do banco de dados (padrão: api-vendas).
   - `ports`: A porta mapeada do host para o contêiner do PostgreSQL (padrão: 5433).

2. Abra o arquivo `src/main/resources/application.properties` e atualize as propriedades do Spring Datasource de acordo com suas preferências:

   - `spring.datasource.url`: URL de conexão com o banco de dados (padrão: jdbc:postgresql://postgres:5432/api-vendas).
   - `spring.datasource.username`: Nome de usuário do banco de dados (padrão: root).
   - `spring.datasource.password`: Senha do banco de dados (padrão: 123456).

# Endpoints da API DE VENDAS

  ## ClienteController
  
1. `POST /clientes`: Cria um cliente.
    - Body: JSON com os detalhes do cliente:
    
  {
  
    "nome": "João",
    "cpf": "12345678901",
    "email": "joao@example.com",
    "endereco": "Rua A, 123"
  }

2. `GET /clientes/all`: Retorna uma lista de todos os clientes.

3. `GET /clientes/{id}`: Retorna os detalhes do cliente conforme o ID informado.

4. `DELETE /clientes/{id}`: Deleta um cliente conforme o ID informado.

5. `PUT /clientes/{id}`: Atualiza um cliente.
    - Body: JSON com os detalhes do cliente:
    
  {
  
    "nome": "João",
    "cpf": "12345678901",
    "email": "joao@example.com",
    "endereco": "Rua A, 123"
  }

  ## ProdutoController
  
1. `POST /produtos`: Cria um produto.
    - Body: JSON com os detalhes do produto:
    
  {
    
    "nome": "Produto 1",
    "preco": 10.0,
    "quantidadeEstoque": 100
  }

2. `GET /produtos/all`: Retorna uma lista com todos os produtos.

3. `GET /produtos/{id}`: Retorna os detalhes de um produto conforme o ID informado.

4. `DELETE /produtos/{id}`: Deleta um cliente conforme o ID informado.

5. `PUT /produtos/{id}`: Atualiza um produto.
    - Body: JSON com os detalhes do produto:
    
  {
    
    "nome": "Produto 1",
    "preco": 10.0,
    "quantidadeEstoque": 100
  }

  ## VendasController
  
1. `POST /vendas`: Cria uma venda.
    - Body: JSON com os detalhes da venda:
    

{
  
        "cliente": {
            "id": 7,
            "nome": "João",
            "cpf": "12345678901",
            "email": "joao@example.com",
            "endereco": "Rua A, 123"
        },
        "produtos": [
            {
                "id": 6,
                "nome": "Calça",
                "descricao": "Calça jeans",
                "preco": 29.99
            }
        ]
}

2. `GET /vendas/all`: Retorna uma lista com todas as vendas.

3. `GET /vendas/{id}`: Retorna os detalhes de uma venda conforme o ID informado.

4. `DELETE /vendas/{id}`: Deleta uma venda conforme o ID informado.

  ## CobrancaController
  
1. `GET /cobrancas`: Retorna uma lista com todas as cobranças.

2. `GET /cobrancas/{id}`: Retorna os detalhes de uma cobrança conforme o ID informado.

3. `DELETE /cobrancas/{id}`: Deleta uma cobrança conforme o ID informado.

# API de Pagamentos

Esta API de pagamentos permite criar, listar, buscar e excluir pagamentos. A API foi desenvolvida usando o framework Spring Boot e o banco de dados PostgreSQL.

# Pré-requisitos

Certifique-se de ter os seguintes requisitos instalados em sua máquina de desenvolvimento:

- Java Development Kit (JDK) 17 
- Apache Maven 3.8.3
- Docker (opcional, apenas se você deseja executar o aplicativo em contêineres Docker)
- Um cliente de API (por exemplo, Postman) para testar as chamadas de API

## Configuração do Banco de Dados

O aplicativo usa um banco de dados PostgreSQL. Antes de executar o aplicativo, você precisa configurar um banco de dados PostgreSQL e fornecer as informações de conexão no arquivo `docker-compose.yml` e no arquivo de propriedades do aplicativo (`application.properties`), conforme explicado abaixo:

1. Abra o arquivo `docker-compose.yml` e atualize as variáveis de ambiente no serviço `postgres` de acordo com suas preferências:

   - `POSTGRES_USER`: O nome de usuário do banco de dados (padrão: root).
   - `POSTGRES_PASSWORD`: A senha do banco de dados (padrão: 123456).
   - `POSTGRES_DB`: O nome do banco de dados (padrão: api-pagamentos).
   - `ports`: A porta mapeada do host para o contêiner do PostgreSQL (padrão: 5433).

2. Abra o arquivo `src/main/resources/application.properties` e atualize as propriedades do Spring Datasource de acordo com suas preferências:

   - `spring.datasource.url`: URL de conexão com o banco de dados (padrão: jdbc:postgresql://postgres:5432/api-pagamentos).
   - `spring.datasource.username`: Nome de usuário do banco de dados (padrão: root).
   - `spring.datasource.password`: Senha do banco de dados (padrão: 123456).

## Endpoints da API DE PAGAMENTOS

1. `POST /pagamentos`: Cria um novo pagamento.
    - Body: JSON com os detalhes do pagamento:

    {
    
       "valor": 50.0
    }

2. `GET /pagamentos/all`: Retorna todos os pagamentos existentes.

3. `GET /pagamentos/{id}`: Retorna os detalhes de um pagamento específico pelo seu ID.

4. `DELETE /pagamentos/{id}`: Exclui um pagamento pelo seu ID.
