📚 AluraFake — Case Técnico Java Pleno
Este projeto foi desenvolvido como parte do desafio técnico para desenvolvedor Java Pleno da Alura. Ele simula parte do domínio da plataforma Alura, focando na modelagem de cursos e atividades interativas, com validações específicas e regras de negócio aplicadas via API REST.

⚙️ Tecnologias Utilizadas
Java 21

Spring Boot

Spring Data JPA

MySQL

Flyway

JUnit 5 + AssertJ + MockMvc

Postman — para testar e documentar os endpoints da API

📦 Estrutura do Projeto
O projeto está dividido em pacotes que representam o domínio da aplicação:

user — modela os usuários da plataforma (estudantes e instrutores)

course — representa os cursos criados por instrutores

task — define as atividades interativas dos cursos, com três tipos:

Resposta Aberta (OPEN_TEXT)

Alternativa Única (SINGLE_CHOICE)

Múltipla Escolha (MULTIPLE_CHOICE)

taskoption — representa as alternativas das atividades de escolha

📌 Funcionalidades Implementadas
✅ Criação de Cursos
Endpoint: POST /course/new

Permite cadastrar um novo curso com nome, descrição e e-mail do instrutor

Valida se o instrutor existe e possui a role adequada

✅ Criação de Atividades
Endpoints:

POST /task/new/opentext

POST /task/new/singlechoice

POST /task/new/multiplechoice

Validações específicas para cada tipo de atividade

Reorganização automática da ordem das atividades se houver conflito

✅ Publicação de Cursos
Endpoint: POST /course/{id}/publish

Regras:

Curso deve estar com status BUILDING

Deve conter ao menos uma atividade de cada tipo

As atividades devem estar em ordem contínua

Atualiza o status para PUBLISHED e define publishedAt

✅ Relatório de Cursos por Instrutor
Endpoint: GET /instructor/{id}/courses

Retorna lista de cursos criados por um instrutor

Inclui quantidade de atividades e total de cursos publicados

🧪 Testes Automatizados
O projeto inclui testes unitários e de integração para garantir o funcionamento das regras de negócio e persistência. Os testes cobrem:

Criação e validação de atividades

Reorganização de ordem

Publicação de cursos

Relatórios por instrutor

📮 Como Testar com Postman
Importe a collection [AluraFake.postman_collection.json](https://postman.co/workspace/My-Workspace~3182c6ff-3d98-468e-8850-03eaab3103d2/collection/19137794-055a6c77-7c9f-4acd-bda5-f2d2044d6c5c?action=share&creator=19137794) no Postman

Configure a variável de ambiente baseUrl como http://localhost:8080

Execute os endpoints na ordem:

Criar usuário instrutor

Criar curso

Adicionar atividades

Publicar curso

Consultar relatório

🔐 Bônus (opcional)
Spring Security: proteção dos endpoints de criação e publicação, acessível apenas por usuários com role INSTRUCTOR

📝 Considerações Finais
Este projeto foi desenvolvido com foco em lógica, orientação a objetos e testes. Toda a interação é feita via API REST, sem interface visual. O código está escrito em inglês, seguindo boas práticas de organização e validação.
