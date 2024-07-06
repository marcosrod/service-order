# Gerenciador de Ordem de Serviço

## Como iniciar a aplicação
- Pré-requisitos: Ter o Docker e Docker Compose instalados no terminal.

1. Entre na pasta do projeto clonada a partir do terminal.
2. Rode o comando: `sudo docker-compose up --build`
3. Aguarde a aplicação buildar e subir os containers.
4. Pronto!

- Caso tenha algum problema, verifique se na sua máquina já estão sendo utilizadas algumas das portas necessárias para o funcionamento dos containers.
- Portas dos microserviços: 8886 e 8887

## Utilização dos endpoints
- A aplicação possui mapeamento de todos os endpoints com Swagger.
- Na Swagger-UI já existe o botão `AUTHORIZE` para que seja inserido um Bearer Token e enviado automaticamente em todas as requests. Para isso, pode-se utilizar as seguintes credenciais:
  - Recepcionista: `{"email": "userRecep@example.com", "password": "passwordR"}`
  - Técnico: `{"email": "userTech@example.com", "password": "passwordT"}`
- Esses usuários são cadastrados automaticamente toda vez que a aplicação sobe, via H2, utilizando um script (schema.sql).

### Para começar a utilizar a aplicação através do Swagger:
1. Entre na Swagger-UI do `authentication-api` (http://localhost:8887/swagger-ui/index.html#/).
2. Envie uma request para o endpoint `[POST] /api/auth` com as credenciais de Recepcionista ou de Técnico (dependerá de quais funcionalidades irá querer utilizar).
3. Depois, pegue o token e insira-o utilizando o botão `AUTHORIZE` no canto superior direito da Swagger-UI.
4. Depois, pegue o mesmo token e insira-o também na Swagger-UI do `service-order-api` (http://localhost:8886/swagger-ui/index.html#/).
5. Pronto! Pode começar a utilizar.

## Estrutura

- A aplicação é constituída por 2 microserviços, ambos utilizando Java 17 e Spring 3, com cobertura de testes unitários (JUnit) principalmente.
- As aplicações têm seus endpoints protegidos com Spring Security, via JWT token.
- A estrutura dos pacotes dentro de cada microserviço, de modo geral, segue: Controller, Dto, Enums, Model, Service, Repository.

### Microserviço 1 - `authentication-api`
Gerencia o cadastro de todos os usuários do sistema, assim como a sua devida autenticação. Para isso, possibilita as seguintes operações:
- Salvar usuário
- Buscar usuários por IDs
- Logar usuário

Os usuários estão divididos em dois tipos (cargos), com suas respectivas funções:

1. Recepcionista: Realiza o cadastro de usuários, clientes, equipamentos e ordens de serviço. Além disso, pode extrair relatórios das ordens de serviço e monitorar o progresso de cada ordem de serviço.
2. Técnico: Pode consultar as suas ordens de serviço pendentes, assim como atualizar o status das ordens de serviço que estão assinaladas para si.

### Microserviço 2 - `service-order-api`
Gerencia as ordens de serviço e suas funcionalidades constituintes (Equipamento, Cliente). Para isso, possibilita as seguintes operações:
- Salvar Cliente
- Salvar Equipamento
- Salvar Ordem de Serviço
- Atualizar Status de Ordem de Serviço (Rastreamento de Progresso)
- Extração de Relatório das ordens de serviço
- Extração de Progresso das ordens de serviço
- Busca das ordens de serviço pendentes (por técnico)
