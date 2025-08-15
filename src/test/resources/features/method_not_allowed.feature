@negativo @metodo
Feature: Método não permitido nos endpoints de leitura da Dog API

  Scenario: POST não permitido em /breeds/list/all
    When eu envio POST para "/breeds/list/all"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breeds/image/random
    When eu envio POST para "/breeds/image/random"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/images/random
    Given a raça "hound"
    When eu envio POST para "/breed/{breed}/images/random"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/images/random/{count}
    Given a raça "pug"
    And o count 3
    When eu envio POST para "/breed/{breed}/images/random/{count}"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/list
    Given a raça "hound"
    When eu envio POST para "/breed/{breed}/list"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/{subBreed}/images
    Given a raça "hound"
    And a sub-raça "afghan"
    When eu envio POST para "/breed/{breed}/{subBreed}/images"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/{subBreed}/images/random
    Given a raça "hound"
    And a sub-raça "afghan"
    When eu envio POST para "/breed/{breed}/{subBreed}/images/random"
    Then a resposta deve indicar metodo nao permitido

  Scenario: POST não permitido em /breed/{breed}/{subBreed}/images/random/{count}
    Given a raça "hound"
    And a sub-raça "afghan"
    And o count 3
    When eu envio POST para "/breed/{breed}/{subBreed}/images/random/{count}"
    Then a resposta deve indicar metodo nao permitido
