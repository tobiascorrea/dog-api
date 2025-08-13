@regressao
Feature: Imagens por raça
  Background:
    Given que configuro a base da Dog API

  @fumaca @basico
  Scenario: Listar imagens de uma raça específica
    Given a raça "pug"
    When eu envio GET para "/breed/{breed}/images"
    Then o status code deve ser 200
    And a lista de imagens não deve estar vazia
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a raça atual

  @regressao @multiplas
  Scenario Outline: Múltiplas imagens por raça com counts válidos
    Given a raça "<breed>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/images/random/{count}"
    Then o status code deve ser 200
    And a quantidade de imagens deve ser exatamente <count>
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a raça atual
    Examples:
      | breed     | count |
      | hound     | 1     |
      | pug       | 3     |
      | retriever | 25    |
      | boxer     | 50    |

  @regressao @multiplas @flexivel
  Scenario: Múltiplas imagens por raça - retorno pode ser menor que solicitado (akita 10)
    Given a raça "akita"
    And o count 10
    When eu envio GET para "/breed/{breed}/images/random/{count}"
    Then o status code deve ser 200
    And deve retornar no máximo 10 imagens
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a raça atual

  @limite
  Scenario Outline: Grande solicitação não deve exceder o count solicitado
    Given a raça "<breed>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/images/random/{count}"
    Then o status code deve ser 200
    And deve retornar no máximo <count> imagens
    And todas as URLs devem ser válidas
    Examples:
      | breed         | count |
      | affenpinscher | 50    |
      | bluetick      | 50    |
      | clumber       | 50    |
      | eskimo        | 50    |
      | otterhound    | 50    |

  @negativo
  Scenario: Raça inválida deve retornar erro
    Given a raça "dragon"
    When eu envio GET para "/breed/{breed}/images"
    Then a resposta não deve ser 200

  @negativo @metodo
  Scenario: Método POST não permitido para múltiplas imagens por raça
    Given a raça "pug"
    And o count 3
    When eu envio POST para "/breed/{breed}/images/random/{count}"
    Then a resposta deve indicar metodo nao permitido
