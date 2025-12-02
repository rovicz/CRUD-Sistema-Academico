# Sistema de Gerenciamento Acadêmico - UNIFAN

Este projeto é uma aplicação desktop desenvolvida como avaliação parcial (P2) para a disciplina de **Programação Orientada a Objetos III** do curso de Engenharia de Software / Segurança da Informação.

O sistema implementa um CRUD completo para o gerenciamento de uma estrutura acadêmica, permitindo o cadastro e relacionamento entre Cursos, Disciplinas, Professores e Turmas.

## 📋 Sobre o Projeto

* **Instituição:** UNIFAN - Centro Universitário Alfredo Nasser
* **Professor:** Daniel Corrêa da Silva
* **Disciplina:** Programação Orientada a Objetos III
* **Avaliação:** P2
* **Objetivo:** Desenvolver uma aplicação utilizando JavaFX, JPA/Hibernate e arquitetura MVC.

## 🚀 Tecnologias Utilizadas

* **Java 17**: Linguagem base.
* **JavaFX 21**: Framework para construção da interface gráfica.
* **Hibernate / JPA**: Framework de ORM para persistência de dados.
* **H2 Database**: Banco de dados em memória (para facilidade de execução e testes).
* **Lombok**: Biblioteca para redução de código boilerplate (Getters, Setters, Construtores).
* **Maven**: Gerenciamento de dependências e build.
* **CSS**: Estilização da interface inspirada no *shadcn/ui*.

## ⚙️ Arquitetura

O projeto segue o padrão arquitetural **MVC (Model-View-Controller)** adaptado, organizado nos seguintes pacotes:

* `src/model`: Entidades JPA (Curso, Disciplina, Professor, Turma).
* `src/view`: Construção das telas em JavaFX.
* `src/controller`: Lógica de controle e integração entre View e DAO.
* `src/dao`: Camada de acesso a dados (Data Access Object) genérica e específica.
* `src/utils`: Utilitários de configuração (JPAUtil, AlertaUtil).

## 🛠️ Como Executar

### Pré-requisitos
* Java JDK 17 ou superior.
* Maven.

### Passos
1.  **Clone ou baixe** o repositório do projeto.
2.  Atualize as dependências do Maven:
    ```bash
    mvn clean install
    ```
3.  Execute a aplicação via Maven:
    ```bash
    mvn javafx:run
    ```
    Ou execute a classe principal `br.com.unifan.MainApp` diretamente pela sua IDE.

## 🗃️ Modelo de Dados (Entidades)

1.  **Curso**: Possui relacionamento 1:N com Disciplinas.
2.  **Disciplina**: Possui relacionamento N:1 com Curso e N:N com Professores.
3.  **Professor**: Possui relacionamento N:N com Disciplinas.
4.  **Turma**: Agrega Disciplina e Professor em um semestre e horário específico.

## 🎨 Interface Gráfica

A interface foi desenhada para ser limpa e funcional, utilizando um arquivo `styles.css` personalizado. O sistema conta com:
* Menu superior para navegação.
* Formulários de cadastro responsivos.
* Tabelas de listagem com atualização dinâmica.
* Feedback visual (Alertas) para operações de Sucesso/Erro.

---
*Desenvolvido em conformidade com os requisitos da avaliação P2.*