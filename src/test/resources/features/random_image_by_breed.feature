@regressao
Feature: Imagem aleatória por raça
  Background:
    Given que configuro a base da Dog API

  @regressao @basico
  Scenario Outline: Obter imagem aleatória para raça válida
    Given a raça "<breed>"
    When eu envio GET para "/breed/{breed}/images/random"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a URL da imagem deve ser valida
    And a URL da imagem deve conter a raça atual
    Examples:
      | breed          |
      | hound          |
      | pug            |
      | retriever      |
      | bulldog        |
      | germanshepherd |

  @negativo @metodo
  Scenario: POST não permitido para imagem aleatória por raça
    Given a raça "hound"
    When eu envio POST para "/breed/{breed}/images/random"
    Then a resposta deve indicar metodo nao permitido
