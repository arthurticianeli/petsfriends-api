# PetsFriends Application

## Descrição
A aplicação PetsFriends é um projeto Spring Boot que gerencia informações sobre pets, permitindo operações CRUD básicas, além de filtragem e atualização de status. Utiliza o PostgreSQL como banco de dados e oferece autenticação JWT para proteção das rotas.

## Tecnologias e Bibliotecas
- Java 17
- Spring Boot 3.3.1
  - spring-boot-starter-data-jpa
  - spring-boot-starter-web
  - spring-boot-starter-security
  - spring-boot-starter-validation
  - spring-boot-devtools
  - spring-boot-starter-test
- PostgreSQL
- Lombok
- Flyway
- JSON Web Token (jjwt) 0.12.6
- JUnit 5.9.3
- Mockito 5.12.0

## Pré-requisitos
- JDK 17
- Docker e Docker Compose
- Maven

## Configuração e Execução

### Banco de Dados
O projeto utiliza PostgreSQL. As configurações padrão podem ser encontradas no arquivo `src/main/resources/application.properties`. Para ajustar à sua configuração local, modifique as propriedades de conexão do banco de dados conforme necessário.

### Docker Compose
Para facilitar o desenvolvimento e o teste, o projeto inclui um arquivo `docker-compose.yml` para subir o banco de dados PostgreSQL.

1. Navegue até o diretório raiz do projeto.
2. Execute o seguinte comando para iniciar o banco de dados:
   ```bash
   docker-compose up -d
   ```
