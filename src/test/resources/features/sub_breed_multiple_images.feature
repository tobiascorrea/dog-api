@regressao @subracas
Feature: Múltiplas imagens por sub-raça
  Background:
    Given que configuro a base da Dog API

  @regressao @multiplas @subracas
  Scenario Outline: Solicitar múltiplas imagens de sub-raça válida
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then o status code deve ser 200
    And a quantidade de imagens deve ser exatamente <count>
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a sub-raça atual
    Examples:
      | breed     | sub     | count |
      | hound     | afghan  | 1     |
      | hound     | afghan  | 3     |
      | bulldog   | english | 5     |
      | mastiff   | bull    | 10    |
      | retriever | golden  | 20    |
      | spaniel   | cocker  | 50    |

  @negativo @subracas
  Scenario Outline: Combinações inválidas não devem retornar 200
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then a resposta não deve ser 200
    Examples:
      | breed         | sub        | count |
      | hound         | invalidSub | 3     |
      | invalidBreed  | afghan     | 5     |
      | retriever     | unknown    | 10    |

  @limite @subracas
  Scenario: Count zero deve retornar nenhuma ou poucas imagens
    Given a raça "hound"
    And a sub-raça "afghan"
    And o count 0
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then a lista de imagens deve estar vazia ou ter até 50 imagens

  @limite @subracas @flexivel
  Scenario Outline: Counts altos devem retornar no máximo o solicitado
    Given a raça "hound"
    And a sub-raça "afghan"
    And o count <count>
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then deve retornar no máximo <count> imagens
    Examples:
      | count |
      | 100   |
      | 999   |

  @negativo @metodo @subracas
  Scenario: POST não permitido para múltiplas imagens de sub-raça
    Given a raça "hound"
    And a sub-raça "afghan"
    And o count 3
    When eu envio POST para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then a resposta deve indicar metodo nao permitido
