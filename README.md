# 🐶 Dog API Automated Test Suite

Este projeto automatiza testes da [Dog CEO API](https://dog.ceo/dog-api/) utilizando **TestNG**, **RestAssured** e relatórios visuais com **ExtentReports**. Ele cobre múltiplos cenários de validação, incluindo retornos válidos, métodos inválidos, sub-raças, quantidades de imagens, e mais — tudo com paralelismo configurável.

---

## 📦 Tecnologias Utilizadas
- Java 21
- Maven
- TestNG
- RestAssured
- JSON Schema Validator
- ExtentReports

---

## 🚀 Como executar o projeto

### 1. Clone o repositório
```bash
git clone https://github.com/tobiascorrea/dog-api
cd dog-api-automation
```

### 2. Instale as dependências
```bash
mvn clean install
```

### 3. Execute todos os testes
```bash
mvn clean test
```

### 4. Execução com paralelismo personalizado
```bash
mvn clean test -Dthreads=4
```
🔁 Esse parâmetro configura dinamicamente o número de threads. Ideal para adaptar a execução em diferentes máquinas (ex: CI/CD ou ambientes locais mais simples).

---

## 📂 Estrutura de Projeto
```
📁 src
 └── test
     ├── java
     │   ├── client/                 -> Cliente da API
     │   ├── config/                 -> Configurações base
     │   ├── dataprovider/          -> DataProviders reutilizáveis
     │   ├── listener/              -> Listener do ExtentReport
     │   ├── tests/                 -> Classes de Teste organizadas por endpoint
     │   └── utils/                 -> Utilitários (ex: ExtentReportManager)
     └── resources
         └── schemas/               -> Schemas JSON para validação de contrato
```

---

## 🧪 Testes Automatizados

### ✅ Cenários Cobertos
- ✅ Listagem de raças e sub-raças
- ✅ Imagens aleatórias por raça e sub-raça
- ✅ Variações de quantidade (count) de imagens
- ✅ Métodos inválidos (POST em endpoints GET)
- ✅ Casos inválidos de sub-raças, caracteres especiais e case sensitivity
- ✅ Validação de contrato com JSON Schema
- ✅ Validação de URLs de imagens
- ✅ Desempenho e tempo de resposta

### 🧠 Sobre as mensagens de asserção nos testes
As mensagens de erro utilizadas nos métodos `Assert` **não são respostas da API**, e sim mensagens **definidas manualmente no código de teste**. Elas servem para:

- Explicar **claramente o que foi validado**;
- Melhorar a leitura dos relatórios de teste;
- Auxiliar na análise de falhas durante debugging ou revisão por outros times.

**Exemplo:**
```java
      Assert.assertFalse(images.isEmpty(), "A lista de imagens não deve estar vazia");
```
Caso a API retorne uma lista vazia, o relatório HTML exibirá essa mensagem personalizada para facilitar o entendimento.

---

## 📊 Relatórios de Teste

Após a execução, um relatório HTML completo será gerado automaticamente em:
```
target/extent-report/extent-report.html
```

### Exibe:
- ✔️ Pass/Fail de cada teste
- ⏱️ Tempo de execução
- 📌 Status HTTP e mensagens esperadas
- 🔎 Validações específicas por URL, parâmetros, schema, etc.

---

## 🐞 Bugs e Inconsistências Encontradas

Durante os testes, foram identificadas as seguintes inconsistências comportamentais da API:

### 1. Diferenciação entre letras maiúsculas e minúsculas
- `GET /breed/HOUND/images` → Retorna 404.
- A API é **case-sensitive**, o que não é comum em RESTful APIs.

### 2. Mesmo parâmetro `count` com valores extremos retorna status `200`, mas lista incompleta
- Quando `count` > 50, algumas raças retornam uma lista menor ou limitada.

### 3. Parâmetro `count` com valor negativo retorna status 200
- `GET /breed/hound/images/random/-3` → Retorna 200.
- A API não deveria aceitar valores negativos no parâmetro `count`, pois são semanticamente inválidos.
- Isso pode causar inconsistência em chamadas programáticas ou relatórios que esperam controle rígido sobre os limites de entrada.
    

Esses comportamentos foram documentados e testados com asserts descritivos para facilitar o rastreio e ajustes futuros.

---

## 👨‍💻 Contribuição
Pull Requests são bem-vindos! Sinta-se à vontade para sugerir melhorias, novos cenários de teste ou integrar com outros frameworks como Allure ou Jenkins.

---

## 📬 Contato
Desenvolvido por [Tobias Correa Camilo] — QA Engineer | Test Automation.  
📧 thobias2501@gmail.com

---

## 🏁 Licença
Este projeto é distribuído sob a licença MIT.