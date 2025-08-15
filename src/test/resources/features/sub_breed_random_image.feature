@regressao @subracas
Feature: Imagem aleatória por sub-raça

  @regressao @basico @subracas
  Scenario Outline: Imagem aleatória válida por sub-raça
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a URL da imagem deve ser valida
    And a URL da imagem deve conter a sub-raça atual
    Examples:
      | breed   | sub     |
      | hound   | afghan  |
      | hound   | basset  |
      | bulldog | english |
      | bulldog | french  |
      | mastiff | bull    |

  @negativo @subracas
  Scenario Outline: Imagem aleatória para sub-raça inválida não deve retornar 200
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    When eu envio GET para "/breed/{breed}/{subBreed}/images/random"
    Then a resposta não deve ser 200
    Examples:
      | breed | sub    |
      | pug   | golden |
      | hound | fake   |
      | xyz   | abc    |

  @variacao @subracas
  Scenario: Duas chamadas consecutivas podem retornar imagens diferentes
    Given a raça "hound"
    And a sub-raça "afghan"
    When eu realizo duas chamadas GET para "/breed/{breed}/{subBreed}/images/random" e comparo os resultados
    Then o status code deve ser 200
