@regressao
Feature: Imagens aleatórias globais
  Background:
    Given que configuro a base da Dog API

  @fumaca @basico
  Scenario: Obter uma imagem aleatória
    When eu envio GET para "/breeds/image/random"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a URL da imagem deve ser valida

  @regressao @multiplas
  Scenario Outline: Obter várias imagens aleatórias com counts válidos
    Given o count <count>
    When eu envio GET para "/breeds/image/random/{count}"
    Then o status code deve ser 200
    And a quantidade de imagens deve ser exatamente <count>
    And todas as URLs devem ser válidas
    Examples:
      | count |
      | 1     |
      | 2     |
      | 10    |
      | 50    |

  @limite
  Scenario: Count acima do limite deve retornar no máximo 50
    Given o count 100
    When eu envio GET para "/breeds/image/random/{count}"
    Then o status code deve ser 200
    And deve retornar no máximo 50 imagens

  @negativo @limite
  Scenario Outline: Counts inválidos não devem ultrapassar 50
    Given o count <count>
    When eu envio GET para "/breeds/image/random/{count}"
    Then deve retornar no máximo 50 imagens
    Examples:
      | count |
      | -1    |
      | 0     |
      | 51    |
      | 100   |

  @negativo @metodo
  Scenario: POST não permitido para imagem aleatória
    When eu envio POST para "/breeds/image/random"
    Then a resposta deve indicar metodo nao permitido
