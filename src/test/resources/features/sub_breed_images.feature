@regressao @subracas
Feature: Imagens por sub-raça
  Background:
    Given que configuro a base da Dog API

  @regressao @basico @subracas
  Scenario Outline: Listar imagens para sub-raça válida
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    When eu envio GET para "/breed/{breed}/{subBreed}/images"
    Then o status code deve ser 200
    And a lista de imagens não deve estar vazia
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a sub-raça atual
    Examples:
      | breed     | sub      |
      | hound     | afghan   |
      | hound     | basset   |
      | bulldog   | english  |
      | bulldog   | french   |
      | retriever | golden   |
      | mastiff   | bull     |

  @negativo @subracas
  Scenario Outline: Sub-raça inválida deve falhar
    Given a raça "<breed>"
    And a sub-raça "<sub>"
    When eu envio GET para "/breed/{breed}/{subBreed}/images"
    Then a resposta não deve ser 200
    Examples:
      | breed    | sub       |
      | pug      | golden    |
      | hound    | fake      |
      | xyz      | abc       |

  @negativo @metodo @subracas
  Scenario: POST não permitido para imagens de sub-raça
    Given a raça "hound"
    And a sub-raça "afghan"
    When eu envio POST para "/breed/{breed}/{subBreed}/images"
    Then a resposta deve indicar metodo nao permitido

  @performance @subracas
  Scenario: Tempo de resposta aceitável para sub-raça
    Given a raça "hound"
    And a sub-raça "afghan"
    When eu envio GET para "/breed/{breed}/{subBreed}/images"
    Then o status code deve ser 200
    And o tempo de resposta deve ser menor que 3000 ms

  @negativo @case @subracas
  Scenario: Sub-raça com letras maiúsculas deve falhar
    Given a raça "HOUND"
    And a sub-raça "AFGHAN"
    When eu envio GET para "/breed/{breed}/{subBreed}/images"
    Then o status code deve ser 404

  @negativo @input @subracas
  Scenario: Sub-raça com caracteres especiais deve falhar
    Given a raça "hound"
    And a sub-raça "afgh@an"
    When eu envio GET para "/breed/{breed}/{subBreed}/images"
    Then a resposta não deve ser 200
