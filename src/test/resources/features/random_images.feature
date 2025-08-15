@regressao
Feature: Imagens aleatórias globais

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
