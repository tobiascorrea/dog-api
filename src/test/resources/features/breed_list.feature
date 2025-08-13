@regressao
Feature: Lista de raças da Dog API
  Como consumidor da Dog API
  Quero consultar a lista de raças
  Para validar estrutura e dados retornados

  Background:
    Given que configuro a base da Dog API

  @fumaca @basico
  Scenario: Validar retorno basico de /breeds/list/all
    When eu envio GET para "/breeds/list/all"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a resposta deve conter as raças:
      | bulldog   |
      | poodle    |
      | retriever |
    And pelo menos uma raça deve possuir sub-raças

  @negativo @metodo
  Scenario: Validar metodo POST nao permitido em /breeds/list/all
    When eu envio POST para "/breeds/list/all"
    Then a resposta deve indicar metodo nao permitido

  @contrato
  Scenario: Validar schema JSON de /breeds/list/all
    When eu envio GET para "/breeds/list/all"
    Then o status code deve ser 200
    And o schema deve ser valido para "schemas/breeds-schema.json"
