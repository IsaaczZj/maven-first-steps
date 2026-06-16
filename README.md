# Maven First Steps - CRUD com JDBC e PostgreSQL

Um projeto Java com Maven que implementa um CRUD basico de cadastros utilizando JDBC puro com PostgreSQL, sem frameworks como Spring ou Hibernate.

## Indice

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Instalacao](#instalacao)
- [Uso](#uso)
- [Operacoes CRUD](#operacoes-crud)
- [Banco de Dados](#banco-de-dados)
- [Desenvolvimento](#desenvolvimento)

## Funcionalidades

- Conexao com PostgreSQL via JDBC
- Fabrica de conexao com padrao Singleton
- Criacao de cadastros no banco de dados
- Listagem de todos os cadastros
- Busca de cadastro por ID
- Atualizacao de cadastro existente
- Remocao de cadastro por ID

## Tecnologias

- Java 21
- Maven
- JDBC
- PostgreSQL 16
- PostgreSQL JDBC Driver 42.7.11
- Docker Compose

## Estrutura do Projeto

```text
maven-first-steps/
├── src/
│   └── main/
│       └── java/maven/estudos/
│           ├── Cadastro.java               # Modelo/entidade de cadastro
│           ├── CadastroRepository.java     # Repositorio JDBC com CRUD completo
│           ├── FabricaConexao.java         # Fabrica de conexao (padrao Singleton)
│           └── SistemaDeCadastro.java      # Classe principal (entry point)
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Instalacao

### 1. Clone o repositorio

```bash
git clone git@github.com:IsaaczZj/maven-first-steps.git
cd maven-first-steps
```

### 2. Suba o banco de dados

```bash
docker compose up -d
```

O `docker-compose.yml` inicia:

- PostgreSQL em `localhost:5432`

Credenciais do banco:

```env
POSTGRES_DB=maven_postgres
POSTGRES_USER=maven_postgres_user
POSTGRES_PASSWORD=maven_postgres_pass
```

### 3. Crie a tabela no banco

Conecte ao PostgreSQL e execute:

```sql
CREATE TABLE public.cadastro (
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(255) NOT NULL,
    idade INTEGER      NOT NULL
);
```

### 4. Execute a aplicacao

```bash
mvn compile exec:java -Dexec.mainClass="maven.estudos.SistemaDeCadastro"
```

## Uso

A classe `SistemaDeCadastro` serve como entry point e demonstra o fluxo completo do CRUD:

1. A conexao com o banco e aberta automaticamente via `FabricaConexao`
2. Um novo cadastro e criado com `save`
3. Todos os cadastros sao listados com `listar`
4. Um cadastro especifico e buscado com `find`
5. O cadastro e atualizado com `patch`
6. O cadastro e removido com `delete`

## Operacoes CRUD

### Criar

```java
Cadastro novo = new Cadastro();
novo.setNome("Joao");
novo.setIdade(25);
repository.save(novo);
```

SQL executado:

```sql
INSERT INTO public.cadastro(nome, idade) VALUES (?, ?)
```

### Listar todos

```java
List<Cadastro> lista = repository.listar();
```

SQL executado:

```sql
SELECT id, nome, idade FROM public.cadastro
```

### Buscar por ID

```java
Cadastro encontrado = repository.find(1);
```

SQL executado:

```sql
SELECT id, nome, idade FROM public.cadastro WHERE id = ?
```

Retorna `null` se nao encontrado.

### Atualizar

```java
encontrado.setNome("Joao Atualizado");
encontrado.setIdade(30);
repository.patch(encontrado);
```

SQL executado:

```sql
UPDATE public.cadastro SET nome = ?, idade = ? WHERE id = ?
```

### Remover

```java
repository.delete(1);
```

SQL executado:

```sql
DELETE FROM public.cadastro WHERE id = ?
```

## Banco de Dados

### Modelo de Cadastro

```java
public class Cadastro {
    private Integer id;
    private String nome;
    private Integer idade;
}
```

### Configuracao da conexao

A conexao e gerenciada pela classe `FabricaConexao` com padrao Singleton:

```java
String url = "jdbc:postgresql://localhost:5432/maven_postgres";
Properties props = new Properties();
props.setProperty("user", "maven_postgres_user");
props.setProperty("password", "maven_postgres_pass");
props.setProperty("ssl", "false");
```

A conexao e criada automaticamente na primeira chamada a `FabricaConexao.getConexao()`, sem necessidade de chamar `conectar()` manualmente.

## Desenvolvimento

### Compilar o projeto

```bash
mvn compile
```

### Gerar o build

```bash
mvn clean package
```

### Verificar dependencias

```bash
mvn dependency:tree
```

## Observacoes

- O projeto utiliza JDBC puro, sem ORM ou frameworks adicionais, ideal para aprender o funcionamento basico de conexao e manipulacao de banco de dados em Java.
- A tabela `cadastro` deve ser criada manualmente antes de executar a aplicacao.
- O PostgreSQL roda na porta `5432`.
