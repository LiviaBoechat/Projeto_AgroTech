# `Projeto Agrotech`

AgroTech, é ma empresa especializada em tecnologias para melhorar a eficiência no cultivo de plantações. Isso visa reduzir
o desperdício de recursos em geral e de alimentos em específico, fazendo um uso mais responsável da
terra disponível para plantio.

O primeiro produto dessa empresa será o Agrix, um sistema que permitirá a gestão e o monitoramento
das fazendas participantes. Esse produto será desenvolvido em fases.



## Habilidades trabalhadas

- Java
- Spring Boot
- Hibernate/JPA
- Spring Data JPA (simplificando relação com db)
- Spring Data (campos de data nas rotas da API e no banco de dados)
- Spring Security (autenticação).
- Maven
- MySQL
- Aplicação a injeção de dependência para conectar as camadas de controle, serviço e persistência.


## Especificações do projeto

<details>
<summary><strong>Testes</strong></summary>
  
- Os testes deste projeto são, de maneira geral, testes de integração. Cada teste fará diversas chamadas à API e validará a resposta e o comportamento da aplicação, mas sem restringir implementações específicas de classes e métodos.
- Os testes do projeto utilizam um banco "mockado" em memória do tipo H2.

</details>

<details>
<summary>Descrição do banco de dados</summary><br>

![Modelo de tabelas](images/agrix-tabelas-fase-b.png)

Nesse modelos, temos as seguintes tabelas:
- `farm`: representa uma fazenda
- `crop`: representa uma plantação, e está em relacionamento `n:1` ("muitos para um") com a tabela `farm`
- `fertilizer`: esta nova tabela representa um fertilizante, e está em um relacionamento `n:n` ("muitos para muitos") com a tabela `crop`. Esse relacionamento é realizado através da tabela `crop_fertilizer`.

</details>


## Rotas

 - GET `/farms`
 - GET `/farms/id`
 - GET `/farms/farmId/crops`
 - POST `/farms`
 - POST `/farms/farmId/crops`
 - GET `/crops`
 - GET `/crops/id`
 - GET `/crops/search`
 - GET `/crops/cropId/fertilizers`
 - POST `/crops/cropId/fertilizers/fertilizerId`
 - GET `/fertilizers`
 - GET `/fertilizers/fertilizerId`
 - POST `/fertilizers`
 - POST `/persons`
 - POST `/auth/login` 


## Autenticação no projeto
 <summary><strong></strong>A autenticação de usuário e senha foi feita através do Spring Security</strong></summary><br />
 
### 1. Garante acesso público (ou seja, desprotegido) aos endpoints:

    - POST `/persons` (permitir cadastro de novas pessoas)
    - POST `/auth/login` (permitir login) 


### 2. Sobre a rota POST `/auth/login`:

    - deve receber o `username` e `password` no corpo da requisição
    - deve validar os dados passados utilizando as pessoas que foram criadas pela rota `/persons`
    - caso os dados estejam incorretos, deve retornar status 403
    - caso os dados estejam corretos, deve retornar um campo `token` contendo um JWT gerado

<details>
  <summary>🔍 Formato/exemplo de requisição e resposta</summary><br />

Exemplo de requisição na rota POST `/auth/login` (suppondo que os dados estejam corretos):

```json
{
  "username": "zerocool",
  "password": "senhasecreta"
}
```

Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhZ3JpeCIsInN1YiI6Im1ycm9ib3QiLCJleHAiOjE2ODk5ODY2NTN9.lyha4rMcMhFd_ij-farGCXuJy-1Tun1IpJd5Ot6z_5w"
}
```

</details>

### 3. Limita acesso à rota GET /farms

<details>
  <summary>Limitar acesso à rota GET /farms para pessoa autenticada com role correto</summary><br />

Limita o acesso à rota GET `/farms` para que apenas uma pessoa autenticada com role `USER`, `MANAGER` ou `ADMIN` possa acessar.

Retorna status 403 caso a pessoa não tenha permissões corretas. Do contrário, a rota deve retornar a resposta usual.

</details>

### 4. Limita acesso à rota GET /crops

<details>
  <summary>Limitar acesso à rota GET /crops para pessoa autenticada com role correto</summary><br />

Apenas uma pessoa autenticada com role `MANAGER` ou `ADMIN` pode acessar.

Deve retornar status 403 caso a pessoa não tenha permissões corretas. Do contrário, a rota deve retornar a resposta usual.

</details>

### 5. Limita acesso à rota GET /fertilizers

<details>
  <summary>Limitar acesso à rota GET /fertilizers para pessoa autenticada com role correto</summary><br />

Deve limitar o acesso à rota GET `/fertilizers` para que apenas uma pessoa autenticada com role `ADMIN` possa acessar.

Retorna status 403 caso a pessoa não tenha permissões corretas. Do contrário, a rota deve retornar a resposta usual.

</details>

## Como Executar

<details>
 <summary><strong> Passo a passo</strong></summary>

  Pré-requisitos: Java 17
  
1. Clone o repositório

- Use o comando: `git clone git@github.com:LiviaBoechat/Projeto_AgroTech.git`
- Entre na pasta do repositório que você acabou de clonar:
    - `cd project-agrix`

2. Instale as dependências

- `mvn install -DskipTests`

3. Execute o projeto
   
- `arquivo: AgrixApplication.java - run as java application`

</details>


<details>
<summary><strong> Checkstyle</strong></summary>

Para garantir a qualidade do código, utilizo neste projeto o `Checkstyle`. Assim o código
estará alinhado com as boas práticas de desenvolvimento, sendo mais legível e de fácil manutenção!
Para poder rodar o `Checkstyle` certifique-se de ter executado o comando `mvn install` dentro do
repositório.

Para rodá-los localmente no repositório, execute os comandos abaixo:

```bash
mvn checkstyle:check
```

Se a análise do `Checkstyle` encontrar problemas no seu código, tais problemas serão mostrados no
seu terminal. Se não houver problema no seu código, nada será impresso no seu terminal.

Você pode também instalar o plugin do `Checkstyle` na sua `IDE`. Para isso, volte na primeira seção
do conteúdo.

</details>

<details>
<summary><strong>Testes</strong></summary>

Para executar todos os testes basta rodar o comando:

```bash
mvn test
```

Para executar apenas uma classe de testes:

```bash
mvn test -Dtest="TestClassName"
```

</details>


<!-- mdi versão 1.1 projeto ⚠️ não exclua esse comentário -->
