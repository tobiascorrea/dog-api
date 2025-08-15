# 🐶 Dog API Test Automation (Cucumber + RestAssured)

Suite de testes da [Dog CEO API](https://dog.ceo/dog-api/) usando:
- Java 21
- Maven
- Cucumber (Gherkin) + cucumber-testng runner
- RestAssured
- JSON Schema Validator
- ExtentReports (adapter cucumber7)

## 📥 Clonar o repositório
Repositório: https://github.com/tobiascorrea/dog-api

```
git clone https://github.com/tobiascorrea/dog-api.git
cd dog-api/dog-api-automation
```

## ✅ Objetivos
Cobrir validações funcionais, negativas, contrato (schema), consistência de URLs e limites (count), mantendo cenários independentes e steps altamente coesos.

## 🧰 Pré-requisitos
- Java 21 (JAVA_HOME configurado)
- Maven 3.9+
- Acesso à internet para a Dog API

## 🚀 Execução
```
mvn clean test
```
Base URL (opcional):
```
mvn test -DbaseUrl=https://dog.ceo/api
```
Filtrar por tags (exemplos):
```
mvn test -Dcucumber.filter.tags="@fumaca"
mvn test -Dcucumber.filter.tags="@regressao and not @known_issue"
```
Relatórios:
- Cucumber HTML: target/cucumber-report.html
- Extent Spark: target/extent-report/extent-cucumber.html

## 🏷️ Tags (principal uso)
- @fumaca @basico: cobertura mínima de saúde
- @regressao: suíte completa
- @multiplas @limite @flexivel: variações de quantidade
- @negativo @metodo @input @case: cenários negativos por tipo
- @subracas: escopo de sub-raças
- @performance: métricas de tempo
- @contrato: validação de schema
- @variacao: verificação de variação entre chamadas

## 🏗️ Arquitetura de Steps (SRP – Single Responsibility Principle)
Cada responsabilidade foi isolada em uma classe de steps, facilitando manutenção e extensão.

| Classe | Responsabilidade |
|--------|------------------|
| CommonSetupSteps | Configuração de base (baseURI) |
| ParameterSteps | Definição de parâmetros (raça, sub-raça, count) |
| HttpRequestSteps | Execução simples de GET/POST + parsing inicial |
| VariationSteps | Duas chamadas consecutivas e comparação |
| StatusSteps | Validações de status HTTP e campo status |
| MethodValidationSteps | Verificação de método não permitido |
| BreedListSteps | Regras específicas da listagem de raças |
| SubBreedListSteps | Regras específicas de sub-raças (lista vazia / com itens) |
| ImageListValidationSteps | Lista de imagens (quantidade, limites, URLs, raça/sub-raça) |
| SingleImageValidationSteps | Validação de imagem única (URL, conteúdo) |
| PerformanceSteps | Tempo de resposta máximo |
| SchemaValidationSteps | Validação de contrato JSON |
| ScenarioContext | Armazena estado por cenário (Response, imagens, parâmetros) |

A antiga classe "ApiGenericSteps" foi descontinuada para evitar acoplamento excessivo.

## 📂 Estrutura Atual (resumida)
```
src/test/java
 ├─ client/              # Cliente HTTP (padrão simples)
 ├─ runner/              # CucumberTestRunner
 ├─ steps/               # Steps coesos (ver tabela)
 ├─ support/             # ScenarioContext
 └─ resources
    ├─ features/         # Cenários Gherkin refinados + tags
    ├─ schemas/          # JSON Schemas
    ├─ extent.properties # Config Extent
    └─ extent-config.xml # Layout do relatório
```

## 🔁 Padrão de Parametrização
Placeholders nos caminhos: `{breed}`, `{subBreed}`, `{count}` são resolvidos dinamicamente antes da requisição.

Exemplo:
```
Given a raça "pug"
And o count 3
When eu envio GET para "/breed/{breed}/images/random/{count}"
Then o status code deve ser 200
And deve retornar no máximo 3 imagens
```

## 🔍 Estratégia de Flexibilização
Alguns endpoints podem retornar menos imagens que o solicitado. Cenários foram marcados com `@flexivel` e usam o step:
```
And deve retornar no máximo X imagens
```
Isso evita falsos negativos mantendo rastreabilidade.

## 🧪 Validações Implementadas (resumo)
- Status HTTP & campo status
- Conteúdo e estrutura de lista de raças/sub-raças
- Quantidade exata vs limite vs flexível
- URLs (https, extensão .jpg, contém raça/sub-raça)
- Método não permitido (POST em endpoints só GET)
- Schema JSON (contrato)
- Tempo de resposta
- Variação entre chamadas consecutivas
- Casos negativos (raça/sub-raça inválida, caracteres especiais, case sensitivity)

## 📊 Relatórios Extent
Config via `extent.properties` + `extent-config.xml`. Ajustes de tema podem ser feitos no XML (theme, reportName, etc.).

## ♻️ Escalabilidade
- Adicionar nova categoria de validação: criar classe Step isolada.
- Reuso garantido pelo ScenarioContext injetado (PicoContainer via cucumber-picocontainer).
- Facilidade para plug‑in de novas métricas (ex: cobertura de headers, tracing IDs).

## 🛠️ Próximos Passos Sugeridos
- Adicionar perfil Maven (ex: smoke) filtrando @fumaca.
- Introduzir Allure para comparação de relatórios.
- Pipeline CI (GitHub Actions) publicando artefatos HTML.
- Tag @known_issue para comportamentos ainda não alinhados com o esperado.

## 🏁 Licença
MIT.
